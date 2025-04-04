package com.medixpress.pharma_service.serviceImpl;

import com.medixpress.pharma_service.constants.Constants;
import com.medixpress.pharma_service.entity.PharmacyMedicines;
import com.medixpress.pharma_service.exception.MediXpressException;
import com.medixpress.pharma_service.repository.PharmacyMedicinesRepository;
import com.medixpress.pharma_service.request.AddMedicineRequest;
import com.medixpress.pharma_service.request.StockUpdateRequest;
import com.medixpress.pharma_service.request.UpdateMedicineRequest;
import com.medixpress.pharma_service.response.Medicine;
import com.medixpress.pharma_service.response.MedicinesResponse;
import com.medixpress.pharma_service.service.MedicineService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MedicineServiceImpl implements MedicineService {

    @Autowired
    private PharmacyMedicinesRepository pharmacyMedicinesRepository;

    @Override
    @Transactional
    public void addMedicine(AddMedicineRequest addMedicineRequest) throws ParseException {
        PharmacyMedicines medicineEntity = new PharmacyMedicines();
        Optional<PharmacyMedicines> pharmacyMedicinesOptionalEntity = pharmacyMedicinesRepository.findByPharmacyIdAndMedicineName(addMedicineRequest.getPharmacyId(), addMedicineRequest.getMedicineName());
        if (pharmacyMedicinesOptionalEntity.isPresent()) {
            throw new MediXpressException("Medicine already exists with the given name: " + addMedicineRequest.getMedicineName(),HttpStatus.CONFLICT.value());
        } else {
            medicineEntity = PharmacyMedicines.builder()
                    .pharmacyId(addMedicineRequest.getPharmacyId())
                    .medicineName(addMedicineRequest.getMedicineName())
                    .manfacturingCompany(addMedicineRequest.getManfacturingCompany())
                    .medicineDescription(addMedicineRequest.getMedicineDescription())
                    .manfacturingDate(new SimpleDateFormat(Constants.DATE_FORMAT).parse(addMedicineRequest.getManfacturingDate()))
                    .expiryDate(new SimpleDateFormat(Constants.DATE_FORMAT).parse(addMedicineRequest.getExpiryDate()))
                    .stock(addMedicineRequest.getStock())
                    .price(addMedicineRequest.getPrice())
                    .build();
            pharmacyMedicinesRepository.save(medicineEntity);
        }
    }

    @Override
    @Transactional
    public void editMedicine(UpdateMedicineRequest updateMedicineRequest) {
        try {
            Optional<PharmacyMedicines> pharmacyMedicinesOptionalEntity = pharmacyMedicinesRepository.findById(updateMedicineRequest.getPharmaMedicineId());
            if (pharmacyMedicinesOptionalEntity.isPresent()) {
                PharmacyMedicines medicineEntity = PharmacyMedicines.builder()
                        .pharmaMedicineId(pharmacyMedicinesOptionalEntity.get().getPharmaMedicineId())
                        .pharmacyId(pharmacyMedicinesOptionalEntity.get().getPharmacyId())
                        .medicineName(pharmacyMedicinesOptionalEntity.get().getMedicineName())
                        .manfacturingCompany(pharmacyMedicinesOptionalEntity.get().getManfacturingCompany())
                        .medicineDescription(pharmacyMedicinesOptionalEntity.get().getMedicineDescription())
                        .manfacturingDate(new SimpleDateFormat(Constants.DATE_FORMAT).parse(updateMedicineRequest.getManfacturingDate()))
                        .expiryDate(new SimpleDateFormat(Constants.DATE_FORMAT).parse(updateMedicineRequest.getExpiryDate()))
                        .stock(updateMedicineRequest.getStock())
                        .price(updateMedicineRequest.getPrice())
                        .build();
                pharmacyMedicinesRepository.save(medicineEntity);
            } else {
                throw new MediXpressException("Medicine not found to update the details: ", HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception e) {
            throw new MediXpressException("Error adding medicine details: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

    }

    @Override
    @Transactional
    public void deleteMedicine(Long pharmacyMedicineId) {
        try {
            Optional<PharmacyMedicines> pharmacyMedicinesOptionalEntity = pharmacyMedicinesRepository.findById(pharmacyMedicineId);
            if (pharmacyMedicinesOptionalEntity.isEmpty()) {
                throw new MediXpressException("Medicine not found to delete the details", HttpStatus.NOT_FOUND.value());
            } else {
                pharmacyMedicinesRepository.deleteById(pharmacyMedicineId);
            }
        } catch (Exception e) {
            throw new MediXpressException("Error adding medicine details: " + e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public Medicine getMedicineById(Long pharmacyMedicineId) {
        Medicine medicineResponse =  new Medicine();
        try {
            Optional<PharmacyMedicines> pharmacyMedicinesOptionalEntity = pharmacyMedicinesRepository.findById(pharmacyMedicineId);
            if (pharmacyMedicinesOptionalEntity.isPresent()) {
                PharmacyMedicines PharmacyMedicine = pharmacyMedicinesOptionalEntity.get();
                medicineResponse =  Medicine.builder()
                        .pharmaMedicineId(PharmacyMedicine.getPharmaMedicineId())
                        .pharmacyId(PharmacyMedicine.getPharmacyId())
                        .medicineName(PharmacyMedicine.getMedicineName())
                        .manfacturingCompany(PharmacyMedicine.getManfacturingCompany())
                        .medicineDescription(PharmacyMedicine.getMedicineDescription())
                        .manfacturingDate( new SimpleDateFormat(Constants.DATE_FORMAT).format(PharmacyMedicine.getManfacturingDate()))
                        .expiryDate(new SimpleDateFormat(Constants.DATE_FORMAT).format(PharmacyMedicine.getExpiryDate()))
                        .stock(PharmacyMedicine.getStock())
                        .price(PharmacyMedicine.getPrice())
                        .build();
            } else {
                throw new MediXpressException("Medicine not found to retrieve its details ", HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception e) {
            throw new MediXpressException("Medicine not found to delete the details ",HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return medicineResponse;
    }

    /**
     * This method helps to retrieve all the medicines of a particular pharmacy
     * @param pharmacyId
     * @return
     */
    @Override
    public MedicinesResponse getAllMedicinesByPharmacyId(Long pharmacyId) {
        MedicinesResponse medicinesResponse = new MedicinesResponse();
        List<Medicine> medicinesList = new ArrayList<>();
        try {
            List<PharmacyMedicines> pharmacyMedicinesEnityList = new ArrayList<>();
             pharmacyMedicinesEnityList = pharmacyMedicinesRepository.findByPharmacyIdAndStock(pharmacyId);
            if (!pharmacyMedicinesEnityList.isEmpty()) {
                for (PharmacyMedicines pharmacyMedicine : pharmacyMedicinesEnityList) {
                    Medicine medicine =  Medicine.builder()
                            .pharmaMedicineId(pharmacyMedicine.getPharmaMedicineId())
                            .pharmacyId(pharmacyMedicine.getPharmacyId())
                            .medicineName(pharmacyMedicine.getMedicineName())
                            .manfacturingCompany(pharmacyMedicine.getManfacturingCompany())
                            .medicineDescription(pharmacyMedicine.getMedicineDescription())
                            .manfacturingDate(pharmacyMedicine.getManfacturingDate().toString())
                            .expiryDate(pharmacyMedicine.getExpiryDate().toString())
                            .stock(pharmacyMedicine.getStock())
                            .price(pharmacyMedicine.getPrice())
                            .build();
                    medicinesList.add(medicine);
                }
                medicinesResponse.setPharmacyMedicines(medicinesList);
            }
        } catch (Exception e) {
            throw new MediXpressException("Error while fetching the medicines that are available in pharmacy",HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return medicinesResponse;
    }

    @Override
    public boolean updateStockByPharmaMedicineId(StockUpdateRequest stockUpdateRequest) {
        stockUpdateRequest.getUpdateStockForPharmaMedicineId().forEach((pharmaMedicineId, stockUpdateValue) -> {
            Optional<PharmacyMedicines> pharmaMedicine = pharmacyMedicinesRepository.findByPharmaMedicineId(pharmaMedicineId);
            if(pharmaMedicine.isPresent()){
                PharmacyMedicines updatedPharmaMedicine = PharmacyMedicines.builder()
                        .pharmaMedicineId(pharmaMedicine.get().getPharmaMedicineId())
                        .pharmacyId(pharmaMedicine.get().getPharmacyId())
                        .medicineName(pharmaMedicine.get().getMedicineName())
                        .manfacturingCompany(pharmaMedicine.get().getManfacturingCompany())
                        .medicineDescription(pharmaMedicine.get().getMedicineDescription())
                        .manfacturingDate(pharmaMedicine.get().getManfacturingDate())
                        .expiryDate(pharmaMedicine.get().getExpiryDate())
                        .stock(pharmaMedicine.get().getStock() - stockUpdateValue)
                        .price(pharmaMedicine.get().getPrice())
                        .build();
                pharmacyMedicinesRepository.save(updatedPharmaMedicine);
            }
        });
        return true;
    }
}
