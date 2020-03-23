package com.logistics.service.view.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
@NoArgsConstructor
public class StockId implements Serializable {

    private String sku;
    private String location;
}
