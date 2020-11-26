package com.logistics.service.controller.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class FlatOrder {

    UUID flatOrderId;

    UUID orderId;

    LocalDateTime localDateTime;

    LocalDateTime localDateTimeEnd;

    UUID orderDetailId;

    String productId;
    int quantity;
    String orderDetailStatus;

    UUID shipmentId;

    String shipFrom;
    String shipTo;
}
