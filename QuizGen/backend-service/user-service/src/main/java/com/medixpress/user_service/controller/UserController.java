package com.medixpress.user_service.controller;

import com.medixpress.user_service.constants.LoginConstant;
import com.medixpress.user_service.exception.MediXpressException;
import com.medixpress.user_service.request.CustomerRequest;
import com.medixpress.user_service.request.LoginRequest;
import com.medixpress.user_service.request.PharmacyRequest;
import com.medixpress.user_service.request.ResetPasswordRequest;
import com.medixpress.user_service.response.ApiResponse;
import com.medixpress.user_service.response.CustomerResponse;
import com.medixpress.user_service.response.LoginResponse;
import com.medixpress.user_service.service.UserService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user-service")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/user/save/customer-register")
    public ResponseEntity<ApiResponse> registerCustomer(@RequestBody CustomerRequest customer) {
        try {
            userService.registerCustomer(customer);
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "Customer registered successfully."));
        } catch (MediXpressException e) {
            return ResponseEntity.status(e.getHttpStatusCode())
                    .body(new ApiResponse(e.getHttpStatusCode(), e.getMessage()));
        }
    }

    @PostMapping("/user/save/pharma-register")
    public ResponseEntity<ApiResponse> registerPharmacy(@RequestBody PharmacyRequest pharmacy){
        try {
            userService.registerPharmacy(pharmacy);
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "Pharma registered successfully."));
        } catch (MediXpressException e) {
            return ResponseEntity.status(e.getHttpStatusCode())
                    .body(new ApiResponse(e.getHttpStatusCode(), e.getMessage()));
        }
    }

    @PostMapping("/user/resetPassword")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest){
        try {
            userService.updateUserPassword(resetPasswordRequest);
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "Password reset successfully."));
        } catch (MediXpressException e) {
            return ResponseEntity.status(e.getHttpStatusCode())
                    .body(new ApiResponse(e.getHttpStatusCode(), e.getMessage()));
        }
    }

    @PostMapping("/user/login")
    public ResponseEntity<ApiResponse> getLogin(@RequestBody LoginRequest loginRequest){
        try {
            LoginResponse loginResponse = userService.getLoginDetails(loginRequest);
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "User logged in successfully",loginResponse));
        } catch (MediXpressException e) {
            return ResponseEntity.status(e.getHttpStatusCode())
                    .body(new ApiResponse(e.getHttpStatusCode(), e.getMessage()));
        }
    }

    /**
     * feign call from order-service to get customer
     * @param customerId
     * @return
     */
    @GetMapping(value = "/user/findByCustomerId/customerId/{customerId}")
    public CustomerResponse getCustomerById(@PathVariable("customerId") Long customerId){
        try{
            return userService.getCustomerById(customerId);
        }catch(MediXpressException e){
            throw new MediXpressException("Failed to retrieve the medicine : "+e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
