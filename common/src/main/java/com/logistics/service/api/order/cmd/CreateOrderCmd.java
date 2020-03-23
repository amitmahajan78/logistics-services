package com.logistics.service.api.order.cmd;

import com.logistics.service.api.cmd.Command;
import com.logistics.service.api.order.domain.Order;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Value
public class CreateOrderCmd implements Command {

    @TargetAggregateIdentifier
    UUID orderUUID;

    Order order;
}
