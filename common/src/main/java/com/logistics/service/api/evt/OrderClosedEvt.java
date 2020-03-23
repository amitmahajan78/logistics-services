package com.logistics.service.api.evt;

import lombok.Value;

import java.util.UUID;

@Value
public class OrderClosedEvt {

    UUID orderDetailId;

    UUID shipmentId;

}
