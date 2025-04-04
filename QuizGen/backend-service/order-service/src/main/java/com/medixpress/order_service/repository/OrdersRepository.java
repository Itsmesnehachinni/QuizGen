package com.medixpress.order_service.repository;

import com.medixpress.order_service.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders,Long> {
    Optional<Orders> findByCustomerIdAndPaymentStatusAndOrderStatus(Long customerId,String paymentStatus,String orderStatus);

    Optional<Orders> findByOrderId(Long orderId);
}
