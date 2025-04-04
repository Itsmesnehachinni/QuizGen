package com.medixpress.pharma_service.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddMedicineRequest {

        @JsonProperty("pharmaMedicineId")
        private Long pharmaMedicineId;

        @NonNull
        @JsonProperty("pharmacyId")
        private Long pharmacyId;

        @NonNull
        @JsonProperty("medicineName")
        private String medicineName;

        @NonNull
        @JsonProperty("manfacturingDate")
        private String manfacturingDate;

        @NonNull
        @JsonProperty("expiryDate")
        private String expiryDate;

        @NonNull
        @JsonProperty("manfacturingCompany")
        private String manfacturingCompany;

        @NonNull
        @JsonProperty("medicineDescription")
        private String medicineDescription;

        @NonNull
        @JsonProperty("stock")
        private Integer stock;

        @NonNull
        @JsonProperty("price")
        private Double price;

    }
