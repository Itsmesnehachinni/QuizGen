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
public class OrderRequest {
    @JsonProperty("orderId")
    private Long orderId;

    @JsonProperty("customerId")
    private Long customerId;

    @JsonProperty("billAmount")
    private Double billAmount;

}
