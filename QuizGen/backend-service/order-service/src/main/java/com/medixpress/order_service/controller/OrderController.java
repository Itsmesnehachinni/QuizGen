package com.medixpress.order_service.controller;

import com.medixpress.order_service.exception.MediXpressException;
import com.medixpress.order_service.response.ApiResponse;
import com.medixpress.order_service.response.OrderResponse;
import com.medixpress.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/order-service")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping(value = "/saveOrder/customerId/{customerId}")
    public ResponseEntity<ApiResponse> saveOrder(@PathVariable("customerId") Long customerId){
        try {
            orderService.saveOrder(customerId);
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "Order placed successfully."));
        } catch (MediXpressException e) {
            return ResponseEntity.status(e.getHttpStatusCode())
                    .body(new ApiResponse(e.getHttpStatusCode(), e.getMessage()));
        }
    }

    @GetMapping("/getOrders/customerId/{customerId}")
    public ResponseEntity<ApiResponse> getOrdersByCustomerId(@PathVariable("customerId") Long customerId){
        try {
            OrderResponse orderResponse = orderService.getOrdersByCustomerId(customerId);
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "Order listed successfully.", orderResponse));
        } catch (MediXpressException e) {
            return ResponseEntity.status(e.getHttpStatusCode())
                    .body(new ApiResponse(e.getHttpStatusCode(), e.getMessage()));
        }
    }

    /**
     * This apis is used to update the order status & payment status after the payment
     * @param orderId
     * @return
     */
    @PostMapping("/updateOrder/orderStatus/{orderId}")
    public ResponseEntity<ApiResponse> updateOrderStatus(@PathVariable("orderId") Long orderId){
        try {
            orderService.updateOrderStatusForOrderId(orderId);
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "Order Status and stock updated successfully."));
        } catch (MediXpressException e) {
            return ResponseEntity.status(e.getHttpStatusCode())
                    .body(new ApiResponse(e.getHttpStatusCode(), e.getMessage()));
        }
    }
}
