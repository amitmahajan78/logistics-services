package com.logistics.service.api.stock.cmd;


import com.logistics.service.api.order.domain.Order;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Value
public class AllocateStock {

    @TargetAggregateIdentifier
    UUID stockCheckUUID;
    UUID orderId;
    UUID orderDetailUUID;
    String sku;
    int quantity;
    String location;
    int stockUpdated;

    Order order;
}
