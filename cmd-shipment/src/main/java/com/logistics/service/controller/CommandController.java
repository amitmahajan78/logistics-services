package com.logistics.service.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.logistics.service.api.cmd.CancelShipmentCmd;
import com.logistics.service.api.utils.AggregateHistoryDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.DomainEventMessage;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.logistics.service.api.cmd.DeliverShipmentCmd;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("v2/shipment")
@RequiredArgsConstructor
public class CommandController {

    private final CommandGateway commandGateway;

    private final EventStore eventStore;

	@ApiOperation(value = "Register shipment arrived", notes = "Send command to mark arrival of shipment at destination")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Updated shipment arrival"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
    @RequestMapping(path = "/registerArrival/{shipmentId}", method = RequestMethod.POST)
    public void registerArrival(@PathVariable("shipmentId") String shipmentId) {
        commandGateway.sendAndWait(new DeliverShipmentCmd(UUID.fromString(shipmentId)));
    }


    @ApiOperation(value = "Register shipment arrived", notes = "Send command to mark cancellation of shipment at destination")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Updated shipment cancellation"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @RequestMapping(path = "/registerCancellation/{shipmentId}", method = RequestMethod.POST)
    public void registerCancellation(@PathVariable("shipmentId") String shipmentId) {
        commandGateway.sendAndWait(new CancelShipmentCmd(UUID.fromString(shipmentId)));
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
