package com.medixpress.order_service.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Medicine {

    private Long pharmaMedicineId;
    private Long pharmacyId;
    private String medicineName;
    private String manfacturingDate;
    private String expiryDate;
    private String manfacturingCompany;
    private String medicineDescription;
    private Integer stock;
    private Double price;

}
