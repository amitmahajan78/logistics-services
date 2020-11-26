package com.logistics.service.repository;

import com.logistics.service.view.model.FlatOrder;
import com.logistics.service.view.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FlatOrderRepository extends JpaRepository<FlatOrder, UUID> {
}
