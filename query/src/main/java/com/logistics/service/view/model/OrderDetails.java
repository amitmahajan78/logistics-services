package com.logistics.service.view.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetails {

    @Id
    @Column(columnDefinition = "uuid", updatable = false)
    UUID orderDetailId;
    String externalReference;
    String productId;
    int quantity;
    String orderDetailStatus;
    UUID orderId;

}
