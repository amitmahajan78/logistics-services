package com.logistics.service.api.evt;

import lombok.Value;

import java.util.UUID;

@Value
public class ShipmentForOrderPreparedEvt {

    UUID orderId;

    UUID shipmentId;

}
