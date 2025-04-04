package com.medixpress.user_service.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequest {

    @JsonProperty("customerId")
    private Long customerId;

    @NonNull
    @JsonProperty("firstname")
    private String firstname;

    @NonNull
    @JsonProperty("lastname")
    private String lastname;

    @NonNull
    @JsonProperty("streetname")
    private String streetname;

    @NonNull
    @JsonProperty("city")
    private String city;

    @JsonProperty("state")
    private String state;

    @JsonProperty("country")
    private String country;

    @NonNull
    @JsonProperty("phone")
    private String phone;

    @NonNull
    @JsonProperty("email")
    private String email;

    @NonNull
    @JsonProperty("postalcode")
    private String postalcode;

    @NonNull
    @JsonProperty("password")
    private String password;

    @JsonProperty("confirmpassword")
    private String confirmpassord;
}
