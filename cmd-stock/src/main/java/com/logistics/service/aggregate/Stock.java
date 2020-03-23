package com.logistics.service.aggregate;


import com.logistics.service.api.stock.cmd.AllocateStock;
import com.logistics.service.api.stock.cmd.CheckStockCmd;
import com.logistics.service.api.stock.evt.StockAllocatedEvt;
import com.logistics.service.api.stock.evt.StockCheckedEvt;
import com.logistics.service.api.stock.evt.StockNotAvailableEvt;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Slf4j
@Aggregate
@NoArgsConstructor
public class Stock {

    @AggregateIdentifier
    UUID stockCheckUUID;

    @CommandHandler
    public Stock(CheckStockCmd checkStockCmd) {

        apply(new StockCheckedEvt(checkStockCmd.getStockCheckUUID(), checkStockCmd.getOrderId(), checkStockCmd.getOrderDetailUUID(),
                checkStockCmd.getSku(), checkStockCmd.getQuantity(), checkStockCmd.getLocation(), checkStockCmd.getOrder()));
    }

    @CommandHandler
    public void handle(AllocateStock allocateStock) {
        if (allocateStock.getStockUpdated() == 0) {
            apply(new StockNotAvailableEvt(allocateStock.getStockCheckUUID(),
                    allocateStock.getOrderId(),
                    allocateStock.getOrderDetailUUID(),
                    allocateStock.getSku(),
                    allocateStock.getQuantity(),
                    allocateStock.getLocation(),
                    allocateStock.getOrder()));
        } else {
            apply(new StockAllocatedEvt(allocateStock.getStockCheckUUID(),
                    allocateStock.getOrderId(),
                    allocateStock.getOrderDetailUUID(),
                    allocateStock.getSku(),
                    allocateStock.getQuantity(),
                    allocateStock.getLocation(),
                    allocateStock.getOrder()));
        }
    }

    @EventSourcingHandler
    void on(StockCheckedEvt stockCheckedEvt) {
        stockCheckUUID = stockCheckedEvt.getStockCheckUUID();
    }

    @EventSourcingHandler
    void on(StockNotAvailableEvt stockNotAvailableEvt) {

    }

    @EventSourcingHandler
    void on(StockAllocatedEvt stockAllocatedEvt) {

    }
}
