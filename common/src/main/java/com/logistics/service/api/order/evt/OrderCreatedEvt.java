package com.logistics.service.api.order.evt;

import com.logistics.service.api.order.domain.Order;
import lombok.Value;

import java.util.UUID;

@Value
public class OrderCreatedEvt {

    UUID orderUUID;

    Order order;
}
