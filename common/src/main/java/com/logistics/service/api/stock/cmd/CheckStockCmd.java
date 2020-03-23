package com.logistics.service.api.stock.cmd;

import com.logistics.service.api.order.domain.Order;
import lombok.Value;

import java.util.UUID;

@Value
public class CheckStockCmd {

    UUID stockCheckUUID;
    UUID orderId;
    UUID orderDetailUUID;
    String sku;
    int quantity;
    String location;

    Order order;

}
