package com.logistics.service.api.stock.evt;

import com.logistics.service.api.order.domain.Order;
import lombok.Value;

import java.util.UUID;

@Value
public class StockAllocatedEvt {

    UUID stockCheckUUID;
    UUID orderId;
    UUID orderDetailUUID;
    String sku;
    int quantity;
    String location;

    Order order;

}
