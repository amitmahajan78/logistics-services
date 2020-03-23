package com.logistics.service.controller.dto;

import com.logistics.service.view.model.OrderDetails;
import lombok.Value;

import java.util.List;

@Value
public class Orders {

    com.logistics.service.view.model.Orders orders;
    List<OrderDetails> orderDetailsList;

}
