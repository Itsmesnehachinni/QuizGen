package com.medixpress.user_service.serviceImpl;

import com.medixpress.user_service.constants.PharmacyStatusEnum;
import com.medixpress.user_service.constants.UserEnum;
import com.medixpress.user_service.entity.Customer;
import com.medixpress.user_service.entity.Pharmacy;
import com.medixpress.user_service.entity.UsersInfo;
import com.medixpress.user_service.exception.MediXpressException;
import com.medixpress.user_service.request.CustomerRequest;
import com.medixpress.user_service.request.LoginRequest;
import com.medixpress.user_service.request.PharmacyRequest;
import com.medixpress.user_service.repository.CustomerRepository;
import com.medixpress.user_service.repository.PharmacyRepository;
import com.medixpress.user_service.repository.UsersInfoRepository;
import com.medixpress.user_service.request.ResetPasswordRequest;
import com.medixpress.user_service.response.CustomerResponse;
import com.medixpress.user_service.response.LoginResponse;
import com.medixpress.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.medixpress.user_service.constants.LoginConstant;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
     @Autowired
     private CustomerRepository customerRepository;
     @Autowired
     private PharmacyRepository pharmacyRepository;
     @Autowired
     private UsersInfoRepository usersInfoRepository;

    @Override
    public void registerCustomer(CustomerRequest customer) {
        Customer customerEntity = null;
        UsersInfo usersInfoEntity = null;
        try{
            validateEmailAddress(customer.getEmail());
            Optional<UsersInfo> userInfoOptionalEntity  =
                    usersInfoRepository.findByUserNameAndRole(customer.getPhone(),UserEnum.CUSTOMER.name());
            if(userInfoOptionalEntity.isPresent()){
                throw new MediXpressException("Customer already registered with the given phone number: " + customer.getPhone(), HttpStatus.CONFLICT.value());
            } else {
                customerEntity = Customer.builder()
                        .customerName(customer.getFirstname()+" "+customer.getLastname())
                        .customerStreetName(customer.getStreetname())
                        .country(customer.getCountry())
                        .postalCode(customer.getPostalcode())
                        .state(customer.getState())
                        .city(customer.getCity())
                        .phoneNumber(customer.getPhone())
                        .emailAddress(customer.getEmail())
                        .build();
                customerRepository.save(customerEntity);
                usersInfoEntity = UsersInfo.builder()
                        .userName(customer.getPhone())
                        .password(customer.getPassword())
                        .role(UserEnum.CUSTOMER.name())
                        .build();
                usersInfoRepository.save(usersInfoEntity);
            }
        }catch(Exception e){
            throw new MediXpressException(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    private void validateEmailAddress(String emailAddress) {
        Customer customer = customerRepository.findByEmailAddress(emailAddress);
        if(customer != null)
            throw new MediXpressException("Customer already registered with the given email address : " + emailAddress, HttpStatus.CONFLICT.value());
    }

    @Override
    public void registerPharmacy(PharmacyRequest pharmacyRequest) {
        try {
            validateLicenseNumberAndEmailAddress(pharmacyRequest.getLicensenumber(),pharmacyRequest.getEmail());
            Optional<UsersInfo> usersInfoOptionalEntity =
                    usersInfoRepository.findByUserNameAndRole(pharmacyRequest.getPhone(),UserEnum.PHARMA_ADMIN.name());
            if (usersInfoOptionalEntity.isPresent()) {
                throw new MediXpressException("Pharma already registered with the given phone number : " + pharmacyRequest.getPhone(), HttpStatus.CONFLICT.value());
            } else {
                Pharmacy pharmacyEntity = Pharmacy.builder()
                        .pharmaName(pharmacyRequest.getPharmaname())
                        .pharmaStreetName(pharmacyRequest.getStreetname())
                        .city(pharmacyRequest.getCity())
                        .state(pharmacyRequest.getState())
                        .country(pharmacyRequest.getCountry())
                        .phoneNumber(pharmacyRequest.getPhone())
                        .emailAddress(pharmacyRequest.getEmail())
                        .licenseNumber(pharmacyRequest.getLicensenumber())
                        .isAlwaysAvailable(true)
                        .status(PharmacyStatusEnum.ACTIVE.name())
                        .build();
                pharmacyRepository.save(pharmacyEntity);
                UsersInfo usersInfoEntity = UsersInfo.builder()
                        .userName(pharmacyRequest.getPhone())
                        .password(pharmacyRequest.getPassword())
                        .role(UserEnum.PHARMA_ADMIN.name())
                        .build();
                usersInfoRepository.save(usersInfoEntity);
            }
        }catch (Exception e){
            throw new MediXpressException(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    private void validateLicenseNumberAndEmailAddress(String licenseNumber,String emailAddress) {
        Pharmacy pharmacy = pharmacyRepository.findByLicenseNumber(licenseNumber);
        if(pharmacy != null)
            throw new MediXpressException("Pharma already registered with the given license number : " + licenseNumber, HttpStatus.CONFLICT.value());
        else{
            Pharmacy pharmacyWithEmail = pharmacyRepository.findByEmailAddress(emailAddress);
            if(pharmacyWithEmail != null)
                throw new MediXpressException("Pharma already registered with the given email address : " + emailAddress, HttpStatus.CONFLICT.value());
        }
    }

    @Override
    public void updateUserPassword(ResetPasswordRequest resetPasswordRequest) {
        try{
            Optional<UsersInfo> usersInfo = usersInfoRepository.findByUserNameAndPassword(resetPasswordRequest.getUserName(),resetPasswordRequest.getOldPassword());
            if(usersInfo.isPresent()){
                UsersInfo usersInfoEntity = UsersInfo.builder()
                        .userId(usersInfo.get().getUserId())
                        .role(usersInfo.get().getRole())
                        .userName(usersInfo.get().getUserName())
                        .password(resetPasswordRequest.getNewPassword())
                        .build();
                usersInfoRepository.save(usersInfoEntity);
            }else{
                throw new MediXpressException("User not found to proceed password reset: " + resetPasswordRequest.getUserName(),HttpStatus.NOT_FOUND.value());
            }
        }catch(Exception e){
            throw new MediXpressException("Error occurred while password reset: " + resetPasswordRequest.getUserName(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public LoginResponse getLoginDetails(LoginRequest loginRequest) {
        try{
            Optional<UsersInfo> usersInfo = usersInfoRepository.findByUserNameAndPassword(loginRequest.getUserName(),loginRequest.getPassword());
            if(usersInfo.isPresent()){
                if(usersInfo.get().getRole().equalsIgnoreCase(UserEnum.CUSTOMER.name())){
                    Customer customer = customerRepository.findByPhoneNumber(loginRequest.getUserName());
                    return LoginResponse.builder()
                            .id(customer.getCustomerId())
                            .password(usersInfo.get().getPassword())
                            .userName(usersInfo.get().getUserName())
                            .role(usersInfo.get().getRole())
                            .build();
                }else if(usersInfo.get().getRole().equalsIgnoreCase(UserEnum.PHARMA_ADMIN.name())){
                    Pharmacy pharmacy = pharmacyRepository.findByPhoneNumber(loginRequest.getUserName());
                    return LoginResponse.builder()
                            .id(pharmacy.getPharmacyId())
                            .password(usersInfo.get().getPassword())
                            .userName(usersInfo.get().getUserName())
                            .role(usersInfo.get().getRole())
                            .build();
                }
            else{
                return LoginResponse.builder().build();
                }
            }else{
                throw new MediXpressException("No User found for logging " + loginRequest.getUserName(),HttpStatus.NOT_FOUND.value());
            }
        }catch(Exception e){
            throw new MediXpressException("No User found for logging " + loginRequest.getUserName(),HttpStatus.NOT_FOUND.value());
        }

    }

    @Override
    public CustomerResponse getCustomerById(Long customerId) {
        try{
            Optional<Customer> customerOptional = customerRepository.findByCustomerId(customerId);
            if(customerOptional.isPresent()){
                return CustomerResponse.builder()
                        .customerId(customerOptional.get().getCustomerId())
                        .customerName(customerOptional.get().getCustomerName())
                        .emailAddress(customerOptional.get().getEmailAddress())
                        .phoneNumber(customerOptional.get().getPhoneNumber())
                        .customerStreetName(customerOptional.get().getCustomerStreetName())
                        .state(customerOptional.get().getState())
                        .city(customerOptional.get().getCity())
                        .country(customerOptional.get().getCountry())
                        .postalCode(customerOptional.get().getPostalCode())
                        .build();
            }
        }catch(Exception e){
            throw new MediXpressException("No User found for customer id " + customerId,HttpStatus.NOT_FOUND.value());
        }
        return null;
    }
}
