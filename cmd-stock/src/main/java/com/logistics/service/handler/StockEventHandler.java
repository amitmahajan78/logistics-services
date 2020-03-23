package com.logistics.service.handler;

import com.logistics.service.api.stock.cmd.AllocateStock;
import com.logistics.service.api.stock.cmd.CheckStockCmd;
import com.logistics.service.api.stock.evt.StockCheckedEvt;
import com.logistics.service.api.stock.evt.StockInquiryCreatedEvt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
@ProcessingGroup("command-processor")
public class StockEventHandler {

    private final CommandGateway commandGateway;

    private final EntityManager entityManager;

    @EventHandler
    public void on(StockInquiryCreatedEvt stockInquiryCreatedEvt) {

        commandGateway.sendAndWait(new CheckStockCmd(UUID.randomUUID(),
                stockInquiryCreatedEvt.getOrderId(),
                stockInquiryCreatedEvt.getOrderDetailId(),
                stockInquiryCreatedEvt.getSku(),
                stockInquiryCreatedEvt.getQuantity(),
                stockInquiryCreatedEvt.getLocation(),
                stockInquiryCreatedEvt.getOrder()));
    }

    @EventHandler
    public void on(StockCheckedEvt stockCheckedEvt) {

        Query query = entityManager.createQuery("UPDATE Stock s set s.quantity = s.quantity - :quantity " +
                "where s.stockId.sku like :sku " +
                "and s.stockId.location like :location " +
                "and s.quantity >= :quantity");

        query.setParameter("quantity", stockCheckedEvt.getQuantity());
        query.setParameter("sku", stockCheckedEvt.getSku());
        query.setParameter("location", stockCheckedEvt.getLocation());
        int rowsUpdated = query.executeUpdate();

        log.info("row updated" + rowsUpdated);

        commandGateway.sendAndWait(new AllocateStock(stockCheckedEvt.getStockCheckUUID(),
                stockCheckedEvt.getOrderId(),
                stockCheckedEvt.getOrderDetailUUID(),
                stockCheckedEvt.getSku(),
                stockCheckedEvt.getQuantity(),
                stockCheckedEvt.getLocation(),
                rowsUpdated,
                stockCheckedEvt.getOrder()));


    }
}
