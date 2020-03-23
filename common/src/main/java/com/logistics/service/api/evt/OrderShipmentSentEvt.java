package com.logistics.service.api.evt;

import com.logistics.service.api.domain.ShipmentDetails;
import lombok.Value;

import java.util.UUID;

@Value
public class OrderShipmentSentEvt {

    ShipmentDetails shipmentDetails;

    UUID shipmentId;

}
