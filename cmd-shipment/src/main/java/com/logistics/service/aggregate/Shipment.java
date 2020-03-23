package com.logistics.service.aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;
import static org.axonframework.modelling.command.AggregateLifecycle.markDeleted;

import java.util.UUID;

import com.logistics.service.api.cmd.*;
import com.logistics.service.api.evt.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import lombok.NoArgsConstructor;

@Aggregate
@NoArgsConstructor
class Shipment {

	@AggregateIdentifier
	private UUID shipmentId;
	private UUID orderDetailsId;

	@CommandHandler
	Shipment(PrepareShipmentCmd cmd) {
		apply(new ShipmentPreparedEvt(cmd.getShipmentId(), cmd.getShipmentDetails()));
	}

	@CommandHandler
	void handle(SendShipmentCmd cmd) {
		apply(new OrderShipmentSentEvt(cmd.getShipmentDetails(), shipmentId));
	}

	@CommandHandler
	void handle(DeliverShipmentCmd cmd) {
		apply(new OrderShipmentDeliveredEvt(shipmentId));
	}

	@CommandHandler
	void handle(CancelShipmentCmd cmd) {
		apply(new OrderShipmentCancelledEvt(shipmentId, orderDetailsId));
	}
	
	@CommandHandler
	void handle(CloseOrderCmd cmd) {
		apply(new OrderClosedEvt(cmd.getOrderId(), cmd.getShipmentId()));
	}


	@EventSourcingHandler
	void on(ShipmentPreparedEvt evt) {
		shipmentId = evt.getShipmentId();
		orderDetailsId = evt.getShipmentDetails().getOrderDetailId();
	}

	@EventSourcingHandler
	void on(OrderClosedEvt evt) {
		markDeleted();
	}

	void on(OrderShipmentCancelledEvt evt)
	{

	}
}
