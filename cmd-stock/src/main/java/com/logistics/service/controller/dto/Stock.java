package com.logistics.service.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class Stock implements Serializable {

    String sku;
    int quantity;
    String supplier;
    String category;
    String productName;
}
