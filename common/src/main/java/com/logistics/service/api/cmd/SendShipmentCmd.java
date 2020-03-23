package com.logistics.service.api.cmd;

import com.logistics.service.api.domain.ShipmentDetails;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Value
public class SendShipmentCmd implements Command {
    
    ShipmentDetails shipmentDetails;

    @TargetAggregateIdentifier
    UUID shipmentId;

}
