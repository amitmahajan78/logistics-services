package com.logistics.service.handler;

import com.logistics.service.api.order.cmd.PlaceOrderCmd;
import com.logistics.service.api.order.domain.OrderDetail;
import com.logistics.service.api.order.evt.OrderCreatedEvt;
import com.logistics.service.api.stock.cmd.CreateStockInquiryCmd;
import com.logistics.service.api.stock.evt.StockAllocatedEvt;
import com.logistics.service.api.stock.evt.StockNotAvailableEvt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;


@Slf4j
@RequiredArgsConstructor
@Component
@ProcessingGroup("command-processor")
public class OrderProcessHandler {

    private final CommandGateway commandGateway;
    
    @EventHandler
    public void on(OrderCreatedEvt orderCreatedEvt) {
        for (OrderDetail orderDetail : orderCreatedEvt.getOrder().getOrderDetailsList()) {

            commandGateway.sendAndWait(new CreateStockInquiryCmd(
                    orderDetail.getOrderId(),
                    orderDetail.getOrderDetailId(),
                    orderDetail.getProductId(),
                    orderDetail.getQuantity(),
                    orderCreatedEvt.getOrder().getBookings().getShipFrom(),
                    orderCreatedEvt.getOrder()));
        }
    }

    @EventHandler
    public void on(StockAllocatedEvt event) {

        commandGateway.sendAndWait(new PlaceOrderCmd(event.getOrderId(),
                event.getOrderDetailUUID(),
                "ExtRef", event.getSku(), event.getQuantity(),
                event.getOrder().getBookings().getBookingId(),
                event.getOrder().getBookings().getVehicleNumber(),
                event.getOrder().getBookings().getDeliveryDateTime(),
                event.getOrder().getBookings().getShipFrom(),
                event.getOrder().getBookings().getShipTo(),
                event.getOrder().getBookings().getBookingCreationDate()
        ));

        log.info("Stock Allocated");
    }

    @EventHandler
    public void on(StockNotAvailableEvt event) {
        log.info("Stock Not Available");
    }
}
