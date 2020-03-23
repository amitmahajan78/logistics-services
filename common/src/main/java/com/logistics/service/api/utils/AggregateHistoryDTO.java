package com.logistics.service.api.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@AllArgsConstructor
@Getter
public class AggregateHistoryDTO {


    private String name;

    private Object event;

    private long sequenceNumber;

    private Instant timestamp;

}
