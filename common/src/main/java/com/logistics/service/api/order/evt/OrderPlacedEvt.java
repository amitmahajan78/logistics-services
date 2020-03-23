package com.logistics.service.api.order.evt;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class OrderPlacedEvt {

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
