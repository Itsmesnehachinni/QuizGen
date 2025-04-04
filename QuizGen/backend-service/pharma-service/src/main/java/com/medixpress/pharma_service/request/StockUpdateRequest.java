package com.medixpress.pharma_service.request;

import lombok.*;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockUpdateRequest {
        private Map<Long,Integer> updateStockForPharmaMedicineId;
}
