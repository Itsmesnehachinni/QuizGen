package com.medixpress.order_service.feignClient;

import com.medixpress.order_service.request.StockUpdateRequest;
import com.medixpress.order_service.response.Medicine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "pharma-service", url = "http://localhost:8082")
public interface PharmaClient {
    @GetMapping(value = "/v1/pharma-service/pharma/findPharmacyMedicineById/pharmacyMedicineId/{pharmacyMedicineId}",
            consumes = "application/json")
    Medicine getPharmacyMedicineById(@PathVariable("pharmacyMedicineId") Long pharmacyMedicineId);

    @PutMapping(value = "/v1/pharma-service/medicine/updateStock")
    boolean updateStockByPharmaMedicineId(@RequestBody StockUpdateRequest stockUpdateRequest);
}
