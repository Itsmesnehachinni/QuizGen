package com.medixpress.pharma_service.service;

import com.medixpress.pharma_service.request.AddMedicineRequest;
import com.medixpress.pharma_service.request.StockUpdateRequest;
import com.medixpress.pharma_service.request.UpdateMedicineRequest;
import com.medixpress.pharma_service.response.Medicine;
import com.medixpress.pharma_service.response.MedicinesResponse;

import java.text.ParseException;

public interface MedicineService {

    void addMedicine(AddMedicineRequest addMedicineRequest) throws ParseException;

    void editMedicine(UpdateMedicineRequest updateMedicineRequest);

    void deleteMedicine(Long pharmacyMedicineId);

    Medicine getMedicineById(Long pharmacyMedicineId);

    MedicinesResponse getAllMedicinesByPharmacyId(Long pharmacyId);

    boolean updateStockByPharmaMedicineId(StockUpdateRequest stockUpdateRequest);
}
