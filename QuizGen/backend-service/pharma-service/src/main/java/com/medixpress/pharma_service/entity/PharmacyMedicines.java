package com.medixpress.pharma_service.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PHARMACY_MEDICINES")
public class PharmacyMedicines {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PHARMA_MEDICINE_ID")
    private Long pharmaMedicineId;

    @Column(name = "PHARMACY_ID")
    private Long pharmacyId;

    @Column(name = "MEDICINE_NAME")
    private String medicineName;

    @Temporal(TemporalType.DATE)
    @Column(name = "MANFACTURING_DATE")
    private Date manfacturingDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "EXPIRY_DATE")
    private Date expiryDate;

    @Column(name = "MANFACTURING_COMPANY")
    private String manfacturingCompany;

    @Column(name = "MEDICINE_DESCRIPTION")
    private String medicineDescription;

    @Column(name = "STOCK")
    private Integer stock;

    @Column(name = "PRICE")
    private Double price;


}
