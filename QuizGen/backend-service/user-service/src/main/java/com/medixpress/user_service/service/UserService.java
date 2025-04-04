package com.medixpress.user_service.service;

import com.medixpress.user_service.request.CustomerRequest;
import com.medixpress.user_service.request.LoginRequest;
import com.medixpress.user_service.request.PharmacyRequest;
import com.medixpress.user_service.request.ResetPasswordRequest;
import com.medixpress.user_service.response.CustomerResponse;
import com.medixpress.user_service.response.LoginResponse;

public interface UserService {

    void registerCustomer(CustomerRequest customerRequest);

    void registerPharmacy(PharmacyRequest pharmacyRequest);

    void updateUserPassword( ResetPasswordRequest resetPasswordRequest);

    LoginResponse getLoginDetails(LoginRequest loginRequest);

    CustomerResponse getCustomerById(Long customerId);
}
