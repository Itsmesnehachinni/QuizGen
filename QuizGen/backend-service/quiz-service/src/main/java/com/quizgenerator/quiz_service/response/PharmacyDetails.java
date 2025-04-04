package com.quizgenerator.quiz_service.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PharmacyDetails {
        private Long pharmacyId;
        private String pharmaName;
        private String pharmaStreetName;
        private String city;
        private String state;
        private String country;
        private String phoneNumber;
        private String emailAddress;
        private String licenseNumber;
        private Boolean isAlwaysAvailable;
        private String status;
    }

