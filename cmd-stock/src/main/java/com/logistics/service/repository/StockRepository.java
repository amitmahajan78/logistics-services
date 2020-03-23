package com.logistics.service.repository;

import com.logistics.service.view.model.Stock;
import com.logistics.service.view.model.StockId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, StockId> {
}
