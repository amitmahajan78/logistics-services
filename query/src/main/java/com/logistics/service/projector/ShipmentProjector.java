package com.logistics.service.projector;

import com.logistics.service.api.evt.OrderClosedEvt;
import com.logistics.service.api.evt.OrderShipmentCancelledEvt;
import com.logistics.service.api.evt.OrderShipmentSentEvt;
import com.logistics.service.api.order.types.OrderStatusType;
import com.logistics.service.view.model.FlatOrder;
import com.logistics.service.view.model.OrderDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
@ProcessingGroup("query-processor")
public class ShipmentProjector {

    private final EntityManager entityManager;

    @Autowired
    private KafkaTemplate<String, OrderDetails> kafkaTemplate;

    @EventHandler
    public void on(OrderShipmentSentEvt evt) {
        updateOrderDetailStatus(OrderStatusType.ORDER_SHIPPED, evt.getShipmentDetails().getOrderDetailId());
        updateBookings(evt.getShipmentDetails().getBookingId(), evt.getShipmentId());


        entityManager.persist(FlatOrder.builder()
                .flatOrderId(UUID.randomUUID())
                .orderId(evt.getShipmentDetails().getOrderId())
                .orderDetailId(evt.getShipmentDetails().getOrderDetailId())
                .shipFrom(evt.getShipmentDetails().getShipFrom())
                .shipTo(evt.getShipmentDetails().getShipTo())
                .productId(evt.getShipmentDetails().getProductId())
                .quantity(evt.getShipmentDetails().getQuantity())
                .orderDetailStatus(OrderStatusType.ORDER_SHIPPED.toString())
                .shipmentId(evt.getShipmentId())
                .localDateTime(LocalDateTime.now())
                .build());
    }

    @EventHandler
    public void on(OrderClosedEvt evt) {

        updateOrderDetailStatus(OrderStatusType.ORDER_CLOSED, evt.getOrderDetailId());

        Query query = entityManager.createQuery("SELECT od FROM OrderDetails od WHERE od.orderDetailId = UUID(:orderDetailId)");

        query.setParameter("orderDetailId", evt.getOrderDetailId());
        OrderDetails orderDetails = (OrderDetails) query.getSingleResult();


        entityManager.persist(FlatOrder.builder()
                .flatOrderId(UUID.randomUUID())
                .orderId(orderDetails.getOrderId())
                .orderDetailId(orderDetails.getOrderDetailId())
                .productId(orderDetails.getProductId())
                .quantity(orderDetails.getQuantity())
                .orderDetailStatus(OrderStatusType.ORDER_CLOSED.toString())
                .shipmentId(evt.getShipmentId())
                .localDateTime(LocalDateTime.now())
                .build());

        try {
            SendResult<String, OrderDetails> sendResult = kafkaTemplate.sendDefault(orderDetails.getOrderDetailId().toString(),
                    orderDetails).get();
            RecordMetadata recordMetadata = sendResult.getRecordMetadata();
            log.info("topic = {}, partition = {}, offset = {}, workUnit = {}",
                    recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset(), orderDetails);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @EventHandler
    public void on(OrderShipmentCancelledEvt evt) {
        updateOrderDetailStatus(OrderStatusType.ORDER_CANCELLED, evt.getOrderDetailId());

        Query query = entityManager.createQuery("SELECT od FROM OrderDetails od WHERE od.orderDetailId = UUID(:orderDetailId)");

        query.setParameter("orderDetailId", evt.getOrderDetailId());
        OrderDetails orderDetails = (OrderDetails) query.getSingleResult();

        entityManager.persist(FlatOrder.builder()
                .flatOrderId(UUID.randomUUID())
                .orderId(orderDetails.getOrderId())
                .orderDetailId(orderDetails.getOrderDetailId())
                .productId(orderDetails.getProductId())
                .quantity(orderDetails.getQuantity())
                .orderDetailStatus(OrderStatusType.ORDER_CANCELLED.toString())
                .shipmentId(evt.getShipmentId())
                .localDateTime(LocalDateTime.now())
                .build());
    }

    private void updateOrderDetailStatus(OrderStatusType stockNotAvailable, UUID orderDetailUUID) {
        Query query = entityManager.createQuery("UPDATE OrderDetails od SET od.orderDetailStatus = :orderDetailStatus" +
                " WHERE od.orderDetailId = UUID(:orderDetailId)");

        query.setParameter("orderDetailStatus", stockNotAvailable.toString());
        query.setParameter("orderDetailId", orderDetailUUID);
        query.executeUpdate();
    }

    private void updateBookings(UUID bookingId, UUID shipmentId) {
        Query query = entityManager.createQuery("UPDATE Bookings b SET b.shipmentId = UUID(:shipmentId) WHERE b.bookingId = UUID(:bookingId)");
        query.setParameter("shipmentId", shipmentId);
        query.setParameter("bookingId", bookingId);
        query.executeUpdate();
    }

}
