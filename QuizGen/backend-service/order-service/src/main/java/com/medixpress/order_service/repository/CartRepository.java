package com.medixpress.order_service.repository;

import com.medixpress.order_service.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

    List<Cart> findByCustomerId (Long customerId);

    void deleteByPharmaMedicineIdAndCustomerId(Long pharmaMedicineId, Long customerId);

    Optional<Cart> findByPharmaMedicineIdAndCustomerId(Long pharmaMedicineId, Long customerId);

    void deleteByCustomerId(Long customerId);

    Optional<List<Cart>> findByCustomerIdAndStatus(Long customerId,String status);
}
