package com.logistics.service.view.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bookings {

    @Id
    UUID bookingId;
    String vehicleNumber;
    LocalDateTime deliveryDateTime;
    String shipFrom;
    String shipTo;
    String shipmentId;
    LocalDateTime bookingCreationDate;
}
