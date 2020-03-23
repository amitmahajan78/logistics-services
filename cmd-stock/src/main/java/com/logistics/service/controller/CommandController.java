package com.logistics.service.controller;

import com.logistics.service.api.utils.AggregateHistoryDTO;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.DomainEventMessage;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("v2/events")
@RequiredArgsConstructor
public class CommandController {

    private final CommandGateway commandGateway;
    private final EventStore eventStore;

    @RequestMapping(method = GET, value = "/{id}/history", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AggregateHistoryDTO> aggregateHistory(
            @PathVariable String id) {
        return eventStore.readEvents(id).asStream().map(this::domainEventToAggregateHistory).collect(Collectors.toList());
    }

    private AggregateHistoryDTO domainEventToAggregateHistory(DomainEventMessage<?> event) {
        return new AggregateHistoryDTO(event.getPayloadType().getSimpleName(), event.getPayload(), event.getSequenceNumber(),
                event.getTimestamp());
    }
}
