package com.medixpress.order_service.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

    private CustomerResponse customer;
    private Double totalBillAmount;
    private Long orderId;
    private List<OrderItemResponse> orderItemResponseList;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class  OrderItemResponse {
        private String medicineName;
        private Integer quantity;
        private Double pricePerItem;
        private Double totalMedicineAmount;
    }

}

