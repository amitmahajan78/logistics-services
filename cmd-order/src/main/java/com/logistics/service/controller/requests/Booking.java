package com.logistics.service.controller.requests;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Validated
public class Booking implements Serializable {

    @NotNull(message = "Vehicle number can not be null")
    @Size(min = 5, max = 10, message
            = "Vehicle number must be between 5 and 10 characters")
    String vehicleNumber;
    @NotNull(message = "Appointment can not be null")
    @Future(message = "Appointment must be in future")
    LocalDateTime appointment;

}
