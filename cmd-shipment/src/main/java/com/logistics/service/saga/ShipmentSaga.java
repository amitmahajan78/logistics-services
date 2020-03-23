package com.logistics.service.saga;

import com.logistics.service.api.cmd.CloseOrderCmd;
import com.logistics.service.api.cmd.PrepareShipmentCmd;
import com.logistics.service.api.cmd.SendShipmentCmd;
import com.logistics.service.api.domain.ShipmentDetails;
import com.logistics.service.api.evt.OrderShipmentDeliveredEvt;
import com.logistics.service.api.evt.ShipmentPreparedEvt;
import com.logistics.service.api.order.evt.OrderPlacedEvt;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.axonframework.modelling.saga.SagaLifecycle.associateWith;

@Slf4j
@Saga
@ProcessingGroup("shipment-command-group")
public class ShipmentSaga {

    @Autowired
    private transient CommandGateway commandGateway;


    private UUID orderDetailId;

    private UUID shipmentId;


    @StartSaga
    @SagaEventHandler(associationProperty = "orderDetailId")
    public void on(OrderPlacedEvt evt) {
        log.debug("handling {}", evt);
        orderDetailId = evt.getOrderDetailId();
        shipmentId = UUID.randomUUID();
        log.debug("shipmentId: {}", shipmentId);
        associateWith("shipmentId", shipmentId.toString());
        commandGateway.send(new PrepareShipmentCmd(shipmentId, new ShipmentDetails(evt.getOrderId(),
                evt.getOrderDetailId(), evt.getExternalReference(), evt.getProductId(),
                evt.getQuantity(), evt.getBookingId(), evt.getVehicleNumber(),
                evt.getDeliveryDateTime(), evt.getShipFrom(), evt.getShipTo(),
                evt.getBookingCreationDate())));
    }

    @SagaEventHandler(associationProperty = "shipmentId")
    public void on(ShipmentPreparedEvt evt) {
        log.debug("handling {}", evt);
        log.debug("shipmentId: {}", shipmentId);
        commandGateway.send(new SendShipmentCmd(evt.getShipmentDetails(), evt.getShipmentId()));
    }

    @SagaEventHandler(associationProperty = "shipmentId")
    @EndSaga
    public void on(OrderShipmentDeliveredEvt evt) {
        log.debug("handling {}", evt);
        log.debug("orderId: {}", orderDetailId);
        log.debug("shipmentId: {}", evt.getShipmentId());
        commandGateway.send(new CloseOrderCmd(orderDetailId, evt.getShipmentId()));
    }
}