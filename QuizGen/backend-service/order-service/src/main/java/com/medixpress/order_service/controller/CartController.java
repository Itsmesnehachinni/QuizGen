package com.medixpress.order_service.controller;

import com.medixpress.order_service.exception.MediXpressException;
import com.medixpress.order_service.request.AddCartRequest;
import com.medixpress.order_service.response.ApiResponse;
import com.medixpress.order_service.response.CartItemsResponse;
import com.medixpress.order_service.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/order-service")
public class CartController {

    @Autowired
    private CartService cartService;

    //create a class called cart and then pass the cart_Items as list

    /**
     * This api helps to add the items to the cart
     * @param addCartRequest
     * @return
     */
    @PostMapping("/cart/addCart")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddCartRequest addCartRequest) {
        try {
            cartService.addToCart(addCartRequest);
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "Items added to cart successfully."));
        } catch (MediXpressException e) {
            return ResponseEntity.status(e.getHttpStatusCode())
                    .body(new ApiResponse(e.getHttpStatusCode(), e.getMessage()));
        }
    }


    /**
     * This api fetches all the items in a cart for a customer
     * @param customerId
     * @return
     */
    @GetMapping("/cart/getAllCartItemsByCustomerId")
    public ResponseEntity<ApiResponse> getAllCartItemsByCustomerId(@RequestParam(name = "customerId") Long customerId) {
        try {
           CartItemsResponse cartItemsResponse =  cartService.getAllCartItemsByCustomerId(customerId);
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "Cart Items retrieved successfully.",cartItemsResponse));
        } catch (MediXpressException e) {
            return ResponseEntity.status(e.getHttpStatusCode())
                    .body(new ApiResponse(e.getHttpStatusCode(), e.getMessage()));
        }
    }

    /**
     * delete the particular medicine from the customers cart
     * @param pharmaMedicineId
     * @param customerId
     * @return
     */
    @DeleteMapping("/cart/deleteByPharmaMedicineId")
    public ResponseEntity<ApiResponse> deleteByPharmaMedicineId(@RequestParam(name = "pharmaMedicineId") Long pharmaMedicineId,
                                                                @RequestParam(name = "customerId") Long customerId) {
        try {
            cartService.deleteByPharmaMedicineId(pharmaMedicineId, customerId);
            CartItemsResponse cartItemsResponse =  cartService.getAllCartItemsByCustomerId(customerId);
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "Medicine deleted from cart successfully",cartItemsResponse));
        } catch (MediXpressException e) {
            return ResponseEntity.status(e.getHttpStatusCode())
                    .body(new ApiResponse(e.getHttpStatusCode(), e.getMessage()));
        }
    }

    /**
     * deletes all the items from the customers cart. this is being used to empty the cart before logout
     * @param customerId
     * @return
     */
    @DeleteMapping("/cart/deleteAllCartItemsByCustomerId")
    public ResponseEntity<ApiResponse> deleteAllCartItemsByCustomerId(@RequestParam(name = "customerId") Long customerId) {
        try {
            cartService.deleteAllCartItemsByCustomerId( customerId);
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "Cart items deleted successfully"));
        } catch (MediXpressException e) {
            return ResponseEntity.status(e.getHttpStatusCode())
                    .body(new ApiResponse(e.getHttpStatusCode(), e.getMessage()));
        }
    }
}
