package com.medixpress.user_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PHARMACY")
public class Pharmacy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PHARMACY_ID")
    private Long pharmacyId;

    @Column(name = "PHARMA_NAME")
    private String pharmaName;

    @Column(name = "PHARMA_STREET_NAME")
    private String pharmaStreetName;

    @Column(name = "CITY")
    private String city;

    @Column(name = "STATE")
    private String state;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;

    @Column(name = "LICENSE_NUMBER")
    private String licenseNumber;

    @Column(name = "IS_ALWAYS_AVAILABLE")
    private Boolean isAlwaysAvailable;

    @Column(name = "STATUS")
    private String status;

}
