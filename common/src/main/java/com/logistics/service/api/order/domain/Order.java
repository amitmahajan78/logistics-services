package com.logistics.service.api.order.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Order {

    UUID orderId;
    String orderType;
    LocalDateTime orderCreationTime;
    User user;
    List<OrderDetail> orderDetailsList = new ArrayList<OrderDetail>();
    Booking bookings;

}
