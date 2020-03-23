package com.logistics.service.controller;


import com.logistics.service.controller.dto.Stock;
import com.logistics.service.repository.StockRepository;
import com.logistics.service.view.model.StockId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("v2/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockRepository stockRepository;

    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> aggregateHistory(@Valid @RequestBody Stock stock) {

        List<String> items = new ArrayList<>();
        items.add("SWINDON");
        items.add("LONDON");
        items.add("CARDIFF");

        for (String item : items) {

            StockId stockId = new StockId(stock.getSku().toString(), item);

            com.logistics.service.view.model.Stock stock1 = new com.logistics.service.view.model.Stock();

            stock1.setCategory(stock.getCategory());
            stock1.setName(stock.getProductName());
            stock1.setQuantity(stock.getQuantity());
            stock1.setStockId(stockId);
            stock1.setSupplier(stock.getSupplier());
            stock1.setTimestamp(LocalDateTime.now());

            stockRepository.save(stock1);
        }


        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(stock.getSku().toString())
                .toUri();

        return ResponseEntity.created(uri).build();

    }

}
