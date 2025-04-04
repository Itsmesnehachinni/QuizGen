package com.medixpress.order_service.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockUpdateRequest {

        private Map<Long,Integer> updateStockForPharmaMedicineId;
}
