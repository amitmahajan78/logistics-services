package com.logistics.service.api.stock.cmd;


import com.logistics.service.api.cmd.Command;
import com.logistics.service.api.order.domain.Order;
import lombok.ToString;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@ToString
@Value
public class CreateStockInquiryCmd implements Command {

    @TargetAggregateIdentifier
    UUID orderId;
    UUID orderDetailUUID;
    String sku;
    int quantity;
    String location;

    Order order;


}
