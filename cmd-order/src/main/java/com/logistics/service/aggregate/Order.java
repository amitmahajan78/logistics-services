package com.logistics.service.aggregate;

import com.logistics.service.api.order.cmd.CreateOrderCmd;
import com.logistics.service.api.order.cmd.PlaceOrderCmd;
import com.logistics.service.api.order.evt.OrderCreatedEvt;
import com.logistics.service.api.order.evt.OrderPlacedEvt;
import com.logistics.service.api.stock.cmd.CreateStockInquiryCmd;
import com.logistics.service.api.stock.evt.StockInquiryCreatedEvt;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor
public class Order {

    @AggregateMember
    com.logistics.service.api.order.domain.Order order;
    @AggregateIdentifier
    private UUID orderId;

    @CommandHandler
    public Order(CreateOrderCmd cmd) {
        apply(new OrderCreatedEvt(cmd.getOrderUUID(), cmd.getOrder()));
    }

    @CommandHandler
    public void handle(CreateStockInquiryCmd createStockInquiryCmd) {
        apply(new StockInquiryCreatedEvt(createStockInquiryCmd.getOrderId(),
                createStockInquiryCmd.getOrderDetailUUID(),
                createStockInquiryCmd.getSku(),
                createStockInquiryCmd.getQuantity(),
                createStockInquiryCmd.getLocation(),
                createStockInquiryCmd.getOrder()
        ));
    }

    @CommandHandler
    public void handle(PlaceOrderCmd cmd) {
        apply(new OrderPlacedEvt(cmd.getOrderId(), cmd.getOrderDetailId(),
                cmd.getExternalReference(), cmd.getProductId(),
                cmd.getQuantity(),
                cmd.getBookingId(),
                cmd.getVehicleNumber(),
                cmd.getDeliveryDateTime(),
                cmd.getShipFrom(),
                cmd.getShipTo(),
                cmd.getBookingCreationDate()));
    }

    @EventSourcingHandler
    void on(OrderCreatedEvt orderCreatedEvt) {
        orderId = orderCreatedEvt.getOrderUUID();
        order = orderCreatedEvt.getOrder();
    }

    @EventSourcingHandler
    void on(StockInquiryCreatedEvt stockInquiryCreatedEvt) {

    }

    @EventSourcingHandler
    void on(OrderPlacedEvt evt) {

    }

}
