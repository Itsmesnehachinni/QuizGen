package com.medixpress.order_service.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemRequest {

    @JsonProperty("pharmaMedicineId")
    private Long pharmaMedicineId;

    @JsonProperty("quantity")
    private Integer quantity;


}
