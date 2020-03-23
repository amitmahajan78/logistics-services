package com.logistics.service.api.order.types;

public enum OrderStatusType {

    // Order Detail Status
    ORDER_RECEIVED,
    STOCK_ALLOCATED,
    STOCK_NOT_AVAILABLE,
    ORDER_PLACED,
    ORDER_SHIPPED,
    ORDER_RECEIVING_CONFIRMED,
    ORDER_CLOSED,
    ORDER_CANCELLED,
    DUPLICATE_ORDER
}
