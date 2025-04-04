package com.medixpress.pharma_service.service;

import com.medixpress.pharma_service.response.Medicine;
import com.medixpress.pharma_service.response.PharmaciesResponse;

public interface PharmaService {

    PharmaciesResponse getAllPharmacies();

    Medicine getPharmacyMedicineById(Long pharmacyMedicineId);
}
