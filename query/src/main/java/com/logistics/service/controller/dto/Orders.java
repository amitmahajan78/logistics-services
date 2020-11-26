package com.logistics.service.controller.dto;

import com.logistics.service.view.model.OrderDetails;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Orders {

    com.logistics.service.view.model.Orders orders = new com.logistics.service.view.model.Orders();
    List<OrderDetails> orderDetailsList = new ArrayList<>();
    String executionTime = "";
}
