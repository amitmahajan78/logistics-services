package com.logistics.service.api.order.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.logistics.service.api.order.evt.OrderCreatedEvt;
import com.logistics.service.api.order.types.OrderStatusType;
import com.logistics.service.api.order.types.OrderTypes;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.EntityId;

import java.time.LocalDateTime;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class OrderDetail {

    UUID orderDetailId;
    UUID orderId;
    String externalReference;
    String productId;
    int quantity;
    String orderDetailStatus;

}
