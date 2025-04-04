package com.medixpress.pharma_service.controller;

import com.medixpress.pharma_service.exception.MediXpressException;
import com.medixpress.pharma_service.request.AddMedicineRequest;
import com.medixpress.pharma_service.request.StockUpdateRequest;
import com.medixpress.pharma_service.request.UpdateMedicineRequest;
import com.medixpress.pharma_service.response.ApiResponse;
import com.medixpress.pharma_service.response.Medicine;
import com.medixpress.pharma_service.response.MedicinesResponse;
import com.medixpress.pharma_service.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/v1/pharma-service")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;


    /**
     * This api allows the pharma admin to add medicines of his pharmacy and returns the list of updated medicines
     * @param addMedicineRequest
     * @return
     */
    @PostMapping("/medicine/addMedicine")
    public ResponseEntity<ApiResponse> addMedicine(@RequestBody AddMedicineRequest addMedicineRequest) throws ParseException {
        try {
            medicineService.addMedicine(addMedicineRequest);
//            MedicinesResponse response = medicineService.getAllMedicinesByPharmacyId(addMedicineRequest.getPharmacyId());
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "Medicine details added successfully."));
        } catch (MediXpressException e) {
            return ResponseEntity.status(e.getHttpStatusCode())
                    .body(new ApiResponse(e.getHttpStatusCode(), e.getMessage()));
        }
    }

    /**
     * This api helps pharma admin to update the medicine details and returns the list of updated medicines
     * @param updateMedicineRequest
     * @return
     */
    @PutMapping("/medicine/editMedicine")
    public ResponseEntity<ApiResponse> editMedicine(@RequestBody UpdateMedicineRequest updateMedicineRequest) {
        try {
            medicineService.editMedicine(updateMedicineRequest);
//            MedicinesResponse response = medicineService.getAllMedicinesByPharmacyId(updateMedicineRequest.getPharmacyId());
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "Medicine details updated successfully."));
        } catch (MediXpressException e) {
            return ResponseEntity.status(e.getHttpStatusCode())
                    .body(new ApiResponse(e.getHttpStatusCode(), e.getMessage()));
        }
    }

    /**
     * This api helps pharma admin to delete the medicine details and returns the list of updated medicines
     * @param pharmacyMedicineId
     * @param pharmacyId
     * @return
     */
    @DeleteMapping("/medicine/deleteMedicine")
    public ResponseEntity<ApiResponse> deleteMedicine(@RequestParam(name = "pharmacyMedicineId") Long pharmacyMedicineId,
                                                      @RequestParam(name = "pharmacyId") Long pharmacyId) {
        try {
            medicineService.deleteMedicine(pharmacyMedicineId);
//            MedicinesResponse response = medicineService.getAllMedicinesByPharmacyId(pharmacyId);
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "Medicine details deleted successfully."));
        } catch (MediXpressException e) {
            return ResponseEntity.status(e.getHttpStatusCode())
                    .body(new ApiResponse(e.getHttpStatusCode(), e.getMessage()));
        }
    }


    /**
     * This api helps to get a particular medicine detail by its pharmacyMedicineId
     * @param pharmacyMedicineId
     * @return
     */
    @GetMapping("/medicine/getMedicineById")
    public ResponseEntity<ApiResponse> getMedicineById(@RequestParam(name = "pharmacyMedicineId") Long pharmacyMedicineId) {
        try {
            Medicine medicineResponse = medicineService.getMedicineById(pharmacyMedicineId);
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "Medicine details are retrieved successfully ", medicineResponse));
        } catch (MediXpressException e) {
            return ResponseEntity.status(e.getHttpStatusCode())
                    .body(new ApiResponse(e.getHttpStatusCode(), e.getMessage()));
        }

    }

    /**
     * This api helps to retrieve all the available medicines (stock > 0) for a pharmacy
     * @param pharmacyId
     * @return
     */
    @GetMapping("/medicine/getAllMedicinesByPharmacyId")
    public ResponseEntity<ApiResponse> getAllMedicinesByPharmacyId(@RequestParam(name = "pharmacyId") Long pharmacyId) {
        try {
            MedicinesResponse response =  medicineService.getAllMedicinesByPharmacyId(pharmacyId);
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "Medicines of pharmacy are successfully retrieved.", response));
        } catch (MediXpressException e) {
            return ResponseEntity.status(e.getHttpStatusCode())
                    .body(new ApiResponse(e.getHttpStatusCode(), e.getMessage()));
        }
    }

    @PutMapping("/medicine/updateStock")
    public boolean updateMedicineByStock(@RequestBody StockUpdateRequest stockUpdateRequest){
        try{
            return medicineService.updateStockByPharmaMedicineId(stockUpdateRequest);
        }catch(MediXpressException e){
            throw new MediXpressException("Failed to update stock : "+e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }


}
