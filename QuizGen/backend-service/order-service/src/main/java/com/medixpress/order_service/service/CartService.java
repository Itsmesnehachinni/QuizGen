package com.medixpress.order_service.service;

import com.medixpress.order_service.request.AddCartRequest;
import com.medixpress.order_service.response.CartItemsResponse;

public interface CartService {

    void addToCart(AddCartRequest addCartRequest);

    CartItemsResponse getAllCartItemsByCustomerId(Long customerId);

    void deleteByPharmaMedicineId(Long pharmaMedicineId , Long customerId);

    void deleteAllCartItemsByCustomerId(Long customerId);
}
