package com.logistics.service.api.evt;

import java.util.UUID;

import lombok.Value;

@Value
public class ShipmentArrivedEvt {

    UUID shipmentId;

}
