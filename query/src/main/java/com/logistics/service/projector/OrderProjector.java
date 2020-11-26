package com.logistics.service.projector;


import com.logistics.service.api.order.domain.Order;
import com.logistics.service.api.order.domain.OrderDetail;
import com.logistics.service.api.order.evt.OrderCreatedEvt;
import com.logistics.service.api.order.evt.OrderPlacedEvt;
import com.logistics.service.api.order.types.OrderStatusType;
import com.logistics.service.api.order.types.OrderTypes;
import com.logistics.service.api.stock.evt.StockAllocatedEvt;
import com.logistics.service.api.stock.evt.StockNotAvailableEvt;
import com.logistics.service.view.model.Bookings;
import com.logistics.service.view.model.FlatOrder;
import com.logistics.service.view.model.OrderDetails;
import com.logistics.service.view.model.Orders;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@ProcessingGroup("query-processor")
public class OrderProjector {

    private final EntityManager entityManager;

    @EventHandler
    public void on(OrderCreatedEvt orderCreatedEvt) {

        // persisting order and bookings
        entityManager.persist(ordersConvertToModel(orderCreatedEvt.getOrder()));

        // persisting each order details
        for (OrderDetail orderDetail : orderCreatedEvt.getOrder().getOrderDetailsList()) {
            entityManager.persist(orderDetailsConvertToModel(orderDetail, orderCreatedEvt.getOrder().getOrderId()));


            entityManager.persist(FlatOrder.builder()
                    .flatOrderId(UUID.randomUUID())
                    .orderId(orderCreatedEvt.getOrderUUID())
                    .orderDetailId(orderDetail.getOrderDetailId())
                    .shipFrom(orderCreatedEvt.getOrder().getBookings().getShipFrom())
                    .shipTo(orderCreatedEvt.getOrder().getBookings().getShipTo())
                    .productId(orderDetail.getProductId())
                    .quantity(orderDetail.getQuantity())
                    .orderDetailStatus(OrderStatusType.ORDER_RECEIVED.toString())
                    .localDateTime(LocalDateTime.now())
                    .build());


        }
    }

    @EventHandler
    public void on(StockNotAvailableEvt event) {
        updateOrderDetailStatus(OrderStatusType.STOCK_NOT_AVAILABLE, event.getOrderDetailUUID());


        entityManager.persist(FlatOrder.builder()
                .flatOrderId(UUID.randomUUID())
                .orderId(event.getOrderId())
                .orderDetailId(event.getOrderDetailUUID())
                .shipFrom(event.getOrder().getBookings().getShipFrom())
                .shipTo(event.getOrder().getBookings().getShipTo())
                .productId(event.getSku())
                .quantity(event.getQuantity())
                .orderDetailStatus(OrderStatusType.STOCK_NOT_AVAILABLE.toString())
                .localDateTime(LocalDateTime.now())
                .build());
    }


    @EventHandler
    public void on(StockAllocatedEvt event) {
        updateOrderDetailStatus(OrderStatusType.STOCK_ALLOCATED, event.getOrderDetailUUID());

        entityManager.persist(FlatOrder.builder()
                .flatOrderId(UUID.randomUUID())
                .orderId(event.getOrderId())
                .orderDetailId(event.getOrderDetailUUID())
                .shipFrom(event.getOrder().getBookings().getShipFrom())
                .shipTo(event.getOrder().getBookings().getShipTo())
                .productId(event.getSku())
                .quantity(event.getQuantity())
                .orderDetailStatus(OrderStatusType.STOCK_ALLOCATED.toString())
                .localDateTime(LocalDateTime.now())
                .build());
    }

    @EventHandler
    public void on(OrderPlacedEvt event) {
        updateOrderDetailStatus(OrderStatusType.ORDER_PLACED, event.getOrderDetailId());

        entityManager.persist(FlatOrder.builder()
                .flatOrderId(UUID.randomUUID())
                .orderId(event.getOrderId())
                .orderDetailId(event.getOrderDetailId())
                .shipFrom(event.getShipFrom())
                .shipTo(event.getShipTo())
                .productId(event.getProductId())
                .quantity(event.getQuantity())
                .orderDetailStatus(OrderStatusType.ORDER_PLACED.toString())
                .localDateTime(LocalDateTime.now())
                .build());
    }

    private Orders ordersConvertToModel(Order order) {


        // populate bookings

        Bookings bookings = Bookings.builder()
                .vehicleNumber(order.getBookings().getVehicleNumber())
                .shipTo(order.getBookings().getShipTo())
                .shipFrom(order.getBookings().getShipFrom())
                .deliveryDateTime(order.getBookings().getDeliveryDateTime())
                .bookingCreationDate(LocalDateTime.now())
                .bookingId(order.getBookings().getBookingId())
                .build();

        // populate Order

        Orders orders = Orders.builder()
                .orderCreationTime(LocalDateTime.now())
                .orderId(order.getOrderId())
                .orderType(OrderTypes.STORE_ORDER.toString())
                .bookings(bookings)
                .build();

        return orders;
    }

    private OrderDetails orderDetailsConvertToModel(OrderDetail orderDetail, UUID orderId) {

        OrderDetails orderDetails = OrderDetails.builder()
                .orderDetailId(orderDetail.getOrderDetailId())
                .orderDetailStatus(OrderStatusType.ORDER_RECEIVED.toString())
                .productId(orderDetail.getProductId())
                .quantity(orderDetail.getQuantity())
                .orderId(orderId)
                .build();

        return orderDetails;
    }

    private void updateOrderDetailStatus(OrderStatusType stockNotAvailable, UUID orderDetailUUID) {
        Query query = entityManager.createQuery("UPDATE OrderDetails od SET od.orderDetailStatus = :orderDetailStatus" +
                " WHERE od.orderDetailId = UUID(:orderDetailId)");

        query.setParameter("orderDetailStatus", stockNotAvailable.toString());
        query.setParameter("orderDetailId", orderDetailUUID);
        query.executeUpdate();
    }
}
