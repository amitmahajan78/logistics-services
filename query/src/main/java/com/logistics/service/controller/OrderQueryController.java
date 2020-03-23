package com.logistics.service.controller;


import com.logistics.service.controller.dto.Orders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Slf4j
@RestController
@RequestMapping("v2/orders")
@RequiredArgsConstructor
public class OrderQueryController {

    @PersistenceContext
    EntityManager entityManager;

    @RequestMapping(method = GET, value = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Orders getOrder(@PathVariable String orderId) {

        // query order and bookings
        Query query1 = entityManager.createQuery("SELECT o FROM Orders o " +
                "WHERE o.orderId = UUID(:orderId)");
        query1.setParameter("orderId", UUID.fromString(orderId));

        // query order details
        Query query2 = entityManager.createQuery("SELECT o FROM OrderDetails o " +
                "WHERE o.orderId = UUID(:orderId)");
        query2.setParameter("orderId", UUID.fromString(orderId));


        return new Orders((com.logistics.service.view.model.Orders) query1.getSingleResult(),
                query2.getResultList());
    }

}
