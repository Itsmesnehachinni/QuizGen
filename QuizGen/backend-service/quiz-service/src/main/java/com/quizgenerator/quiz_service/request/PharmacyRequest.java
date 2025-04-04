package com.quizgenerator.quiz_service.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PharmacyRequest {

    private Long pharmacyId;

    @NonNull
    private String pharmaname;

    @NonNull
    private String streetname;

    @NonNull
    private String city;

    private String state;

    private String country;

    @NonNull
    private String phone;

    @NonNull
    private String email;

    @NonNull
    private String licensenumber;

    private String status;

    @NonNull
    private String password;

    private String postalcode;
}
