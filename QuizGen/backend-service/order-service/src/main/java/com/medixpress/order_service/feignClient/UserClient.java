package com.medixpress.order_service.feignClient;

import com.medixpress.order_service.response.CustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "user-service", url = "http://localhost:8081")
public interface UserClient {
    @GetMapping(value = "/v1/user-service/user/findByCustomerId/customerId/{customerId}")
    CustomerResponse getCustomerById(@PathVariable("customerId") Long customerId);
}
