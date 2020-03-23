package com.logistics.service.controller.requests;


import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Validated
public class Order implements Serializable {

    @NotNull(message = "Ship From can not be null")
    @Size(min = 5, max = 55, message
            = "shipFrom must be between 5 and 55 characters")
    String shipFrom;
    @NotNull(message = "Ship To can not be null")
    @Size(min = 5, max = 55, message
            = "shipTo must be between 5 and 55 characters")
    String shipTo;
    @NotNull(message = "Bookings can not be null")
    @Valid
    Booking booking;
    @NotNull(message = "Order must have at least one product")
    @Valid
    List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
}
