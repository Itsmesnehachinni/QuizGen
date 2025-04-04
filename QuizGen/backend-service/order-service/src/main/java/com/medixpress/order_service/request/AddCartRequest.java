package com.medixpress.order_service.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddCartRequest {

    private Long customerId;
    private List<CartItemRequest> cartItems;
}
