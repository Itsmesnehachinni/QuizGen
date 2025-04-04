package com.medixpress.user_service.controller;

import com.medixpress.user_service.response.PharmaciesResponse;
import com.medixpress.user_service.service.PharmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user-service")
public class PharmaController {

    @Autowired
    PharmaService pharmaService;

    /**
     * feign call from pharma-service to get all active pharmacies
     * @return
     */
    @GetMapping("/pharma/findAllActivePharmacies")
    public PharmaciesResponse getAllPharmacies(){
        return pharmaService.getAllPharmacies();
    }
}
