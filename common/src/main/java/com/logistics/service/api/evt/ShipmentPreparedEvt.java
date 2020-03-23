package com.logistics.service.api.evt;

import java.util.UUID;

import com.logistics.service.api.domain.ShipmentDetails;
import lombok.Value;

@Value
public class ShipmentPreparedEvt {

    UUID shipmentId;

    ShipmentDetails shipmentDetails;

}
