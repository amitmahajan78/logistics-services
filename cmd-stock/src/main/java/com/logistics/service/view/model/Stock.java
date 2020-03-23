package com.logistics.service.view.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stock {

    @EmbeddedId
    StockId stockId;
    int quantity;
    String supplier;
    String category;
    String name;
    LocalDateTime timestamp;

}
