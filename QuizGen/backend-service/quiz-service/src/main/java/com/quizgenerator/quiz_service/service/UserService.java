package com.quizgenerator.quiz_service.service;

import com.quizgenerator.quiz_service.request.CustomerRequest;
import com.quizgenerator.quiz_service.request.LoginRequest;
import com.quizgenerator.quiz_service.request.PharmacyRequest;
import com.quizgenerator.quiz_service.request.ResetPasswordRequest;
import com.quizgenerator.quiz_service.response.CustomerResponse;
import com.quizgenerator.quiz_service.response.LoginResponse;

public interface UserService {

    void registerCustomer(CustomerRequest customerRequest);

    void registerPharmacy(PharmacyRequest pharmacyRequest);

    void updateUserPassword( ResetPasswordRequest resetPasswordRequest);

    LoginResponse getLoginDetails(LoginRequest loginRequest);

    CustomerResponse getCustomerById(Long customerId);
}
