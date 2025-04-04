package com.medixpress.order_service.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemResponse {

    private Long cartId;
    private Long customerId;
    private Long pharmaMedicineId;
    private Integer quantity;
    private String status;
    private String medicineName;
    private String manfacturingCompany;
    private String medicineDescription;
    private Double totalAmount;

}
