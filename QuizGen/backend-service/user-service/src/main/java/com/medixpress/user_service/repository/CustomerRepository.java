package com.medixpress.user_service.repository;

import com.medixpress.user_service.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findByCustomerId(Long customerId);

    Customer findByEmailAddress(String emailAddress);

    Customer findByPhoneNumber(String userName);
}
