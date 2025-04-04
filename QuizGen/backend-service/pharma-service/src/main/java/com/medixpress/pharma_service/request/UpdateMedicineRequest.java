package com.medixpress.pharma_service.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateMedicineRequest {

    @JsonProperty("pharmacyId")
    private Long pharmacyId;

    @NonNull
    @JsonProperty("pharmaMedicineId")
    private Long pharmaMedicineId;

    @JsonProperty("manfacturingDate")
    private String manfacturingDate;

    @JsonProperty("expiryDate")
    private String expiryDate;

    @JsonProperty("stock")
    private Integer stock;

    @JsonProperty("price")
    private Double price;
}
