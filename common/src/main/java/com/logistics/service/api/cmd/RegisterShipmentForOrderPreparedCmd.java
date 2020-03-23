package com.logistics.service.api.cmd;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Value
public class RegisterShipmentForOrderPreparedCmd implements Command {
    
    UUID orderId;

    @TargetAggregateIdentifier
    UUID shipmentId;

}
