package com.logistics.service.controller;

import com.logistics.service.api.utils.AggregateHistoryDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.DomainEventMessage;
import org.axonframework.eventsourcing.eventstore.DomainEventStream;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("v2/orders/command")
@RequiredArgsConstructor
public class CommandController {

    private final CommandGateway commandGateway;
    private final EventStore eventStore;


    @RequestMapping(method = GET, value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> history(
            @PathVariable String id) {
        List<String> retour = new ArrayList<>();
        DomainEventStream domainEventStream = eventStore.readEvents(id);
        while (domainEventStream.hasNext()) {
            DomainEventMessage<?> event = domainEventStream.next();
            retour.add(event.getPayload().toString());
        }
        return retour;
    }

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
