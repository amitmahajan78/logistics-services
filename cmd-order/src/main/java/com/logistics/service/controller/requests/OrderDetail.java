package com.logistics.service.controller.requests;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@Validated
public class OrderDetail implements Serializable {

    @NotNull(message = "SKU can not be null")
    @Size(min = 9, max = 55, message
            = "SKU must be between 9 and 55 characters")
    String sku;
    @NotNull(message = "Quantity can not be null")
    @Positive(message = "Quantity must be positive number")
    int quantity;
}
