package com.medixpress.pharma_service.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PharmacyRequest {

    @JsonProperty("pharmacyId")
    private Long pharmacyId;

    @NonNull
    @JsonProperty("pharmaName")
    private String pharmaName;

    @NonNull
    @JsonProperty("pharmaStreetName")
    private String pharmaStreetName;

    @NonNull
    @JsonProperty("city")
    private String city;

    @JsonProperty("state")
    private String state;

    @JsonProperty("country")
    private String country;

    @NonNull
    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @NonNull
    @JsonProperty("emailAddress")
    private String emailAddress;

    @NonNull
    @JsonProperty("licenseNumber")
    private String licenseNumber;

    @JsonProperty("isAlwaysAvailable")
    private Boolean isAlwaysAvailable;

    @JsonProperty("status")
    private String status;

    @NonNull
    @JsonProperty("password")
    private String password;
}
