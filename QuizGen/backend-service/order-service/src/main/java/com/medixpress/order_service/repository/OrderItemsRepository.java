package com.medixpress.order_service.repository;

import com.medixpress.order_service.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems,Long> {
    List<OrderItems> findByOrderId(Long orderId);
}
