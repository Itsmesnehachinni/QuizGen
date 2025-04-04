package com.medixpress.pharma_service.repository;

import com.medixpress.pharma_service.entity.PharmacyMedicines;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PharmacyMedicinesRepository extends JpaRepository<PharmacyMedicines,Long> {

    Optional<PharmacyMedicines> findByPharmacyIdAndMedicineName(Long pharmacyId, String medicineName);

    @Query("SELECT p FROM PharmacyMedicines p WHERE p.stock > 0 and p.pharmacyId = :pharmacyId")
    List<PharmacyMedicines> findByPharmacyIdAndStock(Long pharmacyId);

    @Query("SELECT p FROM PharmacyMedicines p WHERE p.stock > 0")
    List<PharmacyMedicines> findAllAvailableMedicines();

    Optional<PharmacyMedicines> findByPharmaMedicineId(Long pharmacyMedicineId);


}
