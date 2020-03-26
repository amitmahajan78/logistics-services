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
import java.util.ArrayList;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Slf4j
@RestController
@RequestMapping("v2/query/orders")
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

    // SELECT * FROM orders order by order_creation_time DESC LIMIT 10;

    @RequestMapping(method = GET, value = "/list/{limit}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList getLatestOrdersWithLimit(@PathVariable int limit) {

        // query order and bookings
        Query query1 = entityManager.createQuery("SELECT o FROM Orders o order by o.orderCreationTime DESC");
        query1.setMaxResults(limit);

        ArrayList<Object> orderList = new ArrayList<>();

        for (Object order : query1.getResultList()) {
            com.logistics.service.view.model.Orders orders1 = (com.logistics.service.view.model.Orders) order;


            // query order details
            Query query2 = entityManager.createQuery("SELECT o FROM OrderDetails o " +
                    "WHERE o.orderId = UUID(:orderId)");
            query2.setParameter("orderId", orders1.getOrderId());

            ArrayList<Object> orderDetailList = new ArrayList<>();
            orderDetailList.add(orders1);
            orderDetailList.add(query2.getResultList());

            orderList.add(orderDetailList);

        }


        return orderList;
    }

}
