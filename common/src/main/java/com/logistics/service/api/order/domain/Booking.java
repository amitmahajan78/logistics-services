package com.logistics.service.api.order.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.axonframework.modelling.command.EntityId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Booking {

    UUID bookingId;
    String vehicleNumber;
    LocalDateTime deliveryDateTime;
    String shipFrom;
    String shipTo;
    UUID shipmentId;
    LocalDateTime bookingCreationDate;
    UUID orderId;

}
