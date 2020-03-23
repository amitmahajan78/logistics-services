package com.logistics.service.api.cmd;

import java.util.UUID;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Value;

@Value
public class CloseOrderCmd implements Command {

	UUID orderId;
	
	@TargetAggregateIdentifier
	UUID shipmentId;
}
