package com.medixpress.user_service.repository;

import com.medixpress.user_service.entity.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy,Long> {
    List<Pharmacy> findByStatus(String status);

    Pharmacy findByLicenseNumber(String licenseNumber);

    Pharmacy findByEmailAddress(String emailAddress);

    Pharmacy findByPhoneNumber(String userName);
}
