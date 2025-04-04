package com.medixpress.user_service.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {

    private Long customerId;

    private String customerStreetName;

    private String  customerName;

    private String city;

    private String state;

    private String country;

    private String phoneNumber;

    private String emailAddress;

    private String postalCode;

}
