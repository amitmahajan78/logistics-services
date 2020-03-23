package com.logistics.service.api.order.cmd;

import com.logistics.service.api.cmd.Command;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class PlaceOrderCmd implements Command {

    @TargetAggregateIdentifier
    UUID orderId;

    UUID orderDetailId;
    String externalReference;
    String productId;
    int quantity;

    UUID bookingId;
    String vehicleNumber;
    LocalDateTime deliveryDateTime;
    String shipFrom;
    String shipTo;
    LocalDateTime bookingCreationDate;

}
