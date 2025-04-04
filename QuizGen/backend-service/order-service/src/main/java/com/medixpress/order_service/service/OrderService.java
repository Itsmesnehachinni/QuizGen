package com.medixpress.order_service.service;

import com.medixpress.order_service.response.OrderResponse;

public interface OrderService {

    void saveOrder(Long customerId);
    OrderResponse getOrdersByCustomerId(Long customerId);

    void updateOrderStatusForOrderId(Long orderId);
}
