package com.logistics.service.controller;

import com.google.common.base.Preconditions;
import com.logistics.service.api.order.cmd.CreateOrderCmd;
import com.logistics.service.api.order.domain.Booking;
import com.logistics.service.api.order.domain.OrderDetail;
import com.logistics.service.api.order.types.OrderStatusType;
import com.logistics.service.api.order.types.OrderTypes;
import com.logistics.service.controller.requests.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("v2/orders")
@RequiredArgsConstructor
public class OrderController {


    private final CommandGateway commandGateway;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> placeOrder(@Valid @RequestBody Order order) {

        Preconditions.checkNotNull(order);

        UUID orderUUID = UUID.randomUUID();

        commandGateway.sendAndWait(new CreateOrderCmd(orderUUID,
                convertToDomain(order, orderUUID)));


        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(orderUUID)
                .toUri();

        return ResponseEntity.created(uri).build();
    }


    private com.logistics.service.api.order.domain.Order convertToDomain(Order order, UUID orderUUID) {


        // populate OrderDetail
        List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();

        for (com.logistics.service.controller.requests.OrderDetail orderDetail : order.getOrderDetailList()) {
            OrderDetail orderDetail1 = OrderDetail.builder()
                    .orderDetailId(UUID.randomUUID())
                    .orderDetailStatus(OrderStatusType.ORDER_RECEIVED.toString())
                    .productId(orderDetail.getSku())
                    .quantity(orderDetail.getQuantity())
                    .orderId(orderUUID)
                    .build();
            orderDetailList.add(orderDetail1);
        }

        // populate bookings

        Booking bookings = Booking.builder()
                .vehicleNumber(order.getBooking().getVehicleNumber())
                .shipTo(order.getShipTo())
                .shipFrom(order.getShipFrom())
                .deliveryDateTime(order.getBooking().getAppointment())
                .bookingCreationDate(LocalDateTime.now())
                .bookingId(UUID.randomUUID())
                .orderId(orderUUID)
                .build();

        // populate Order

        com.logistics.service.api.order.domain.Order order1 = com.logistics.service.api.order.domain.Order.builder()
                .orderCreationTime(LocalDateTime.now())
                .orderDetailsList(orderDetailList)
                .orderId(orderUUID)
                .orderType(OrderTypes.STORE_ORDER.toString())
                .bookings(bookings)
                .build();

        return order1;
    }
}
