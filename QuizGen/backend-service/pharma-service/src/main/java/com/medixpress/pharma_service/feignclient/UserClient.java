package com.medixpress.pharma_service.feignclient;

import com.medixpress.pharma_service.response.PharmaciesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "user-service", url = "http://localhost:8081")
public interface UserClient {
    @GetMapping(value = "/v1/user-service/pharma/findAllActivePharmacies")
    PharmaciesResponse getAllPharmacies();
}
