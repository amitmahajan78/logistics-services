package com.logistics.service.api.cmd;

import com.logistics.service.api.domain.ShipmentDetails;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class PrepareShipmentCmd implements Command {

    @TargetAggregateIdentifier
    UUID shipmentId;

    ShipmentDetails shipmentDetails;
}
