package com.logistics.service.api.cmd;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Value
public class CancelShipmentCmd implements Command {

    @TargetAggregateIdentifier
    UUID shipmentId;

}
