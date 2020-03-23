package com.logistics.service.view.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orders {

    @Id
    UUID orderId;
    String orderType;
    LocalDateTime orderCreationTime;
    @OneToOne(cascade = CascadeType.ALL)
    Bookings bookings;
}
