package com.logistics.service.view.model;


import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlatOrder {

    @Id
    UUID flatOrderId;

    UUID orderId;

    LocalDateTime localDateTime;

    UUID orderDetailId;

    String productId;
    int quantity;
    String orderDetailStatus;

    UUID shipmentId;

    String shipFrom;
    String shipTo;


}
