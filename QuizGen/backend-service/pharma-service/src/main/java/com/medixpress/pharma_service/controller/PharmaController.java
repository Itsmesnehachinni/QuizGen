package com.medixpress.pharma_service.controller;

import com.medixpress.pharma_service.exception.MediXpressException;
import com.medixpress.pharma_service.response.ApiResponse;
import com.medixpress.pharma_service.response.Medicine;
import com.medixpress.pharma_service.response.PharmaciesResponse;
import com.medixpress.pharma_service.service.PharmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/pharma-service")
public class PharmaController {

    @Autowired
    private PharmaService pharmaService;

    // check if the logged in used is not pharma_admin. only admin and customer can see the details

    /**
     * This api retrieves all the pharmacy details with list of medicines(In-stock)
     * @return
     */
    @GetMapping("/pharma/getAllPharmacies")
    public ResponseEntity<ApiResponse> getAllPharmacies() {
        try {
            PharmaciesResponse pharmaciesResponse = pharmaService.getAllPharmacies();
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "All Pharmacy details retrieved successfully.", pharmaciesResponse));
        } catch (MediXpressException e) {
            return ResponseEntity.status(e.getHttpStatusCode())
                    .body(new ApiResponse(e.getHttpStatusCode(), e.getMessage()));
        }
    }

    /**
     * Feign call from order-service
     * @param pharmacyMedicineId
     * @return
     */

    @GetMapping(value = "/pharma/findPharmacyMedicineById/pharmacyMedicineId/{pharmacyMedicineId}",
            produces = "application/json")
    public Medicine getPharmacyMedicineById(@PathVariable("pharmacyMedicineId") Long pharmacyMedicineId){
        try{
            return pharmaService.getPharmacyMedicineById(pharmacyMedicineId);
        }catch(MediXpressException e){
            throw new MediXpressException("Failed to retrieve the medicine : "+e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }


}
