package com.medixpress.order_service.serviceImpl;

import com.medixpress.order_service.constants.CartEnum;
import com.medixpress.order_service.entity.Cart;
import com.medixpress.order_service.exception.MediXpressException;
import com.medixpress.order_service.feignClient.PharmaClient;
import com.medixpress.order_service.repository.CartRepository;
import com.medixpress.order_service.request.AddCartRequest;
import com.medixpress.order_service.request.CartItemRequest;
import com.medixpress.order_service.response.CartItemResponse;
import com.medixpress.order_service.response.CartItemsResponse;
import com.medixpress.order_service.response.Medicine;
import com.medixpress.order_service.service.CartService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private PharmaClient pharmaClient;

    /**
     * This method helps to add all the items to the cart for a customer
     * @param addCartRequest
     */
    @Override
    public void addToCart(AddCartRequest addCartRequest) {
        try{
            List<Cart> cartEntityList = new ArrayList<>();
            for(CartItemRequest item: addCartRequest.getCartItems()){
                Cart cartEntity = Cart.builder()
                        .customerId(addCartRequest.getCustomerId())
                        .pharmaMedicineId(item.getPharmaMedicineId())
                        .quantity(item.getQuantity())
                        .status(CartEnum.INPROGRESS.name())
                        .build();
                cartEntityList.add(cartEntity);
            }
            cartRepository.saveAll(cartEntityList);
        }catch (Exception e) {
            throw new MediXpressException("Error adding items to cart: " + e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    /**
     * This method helps to get all the cart items for a customer
     * @param customerId
     * @return
     */
    @Override
    public CartItemsResponse getAllCartItemsByCustomerId(Long customerId) {
        try{
            CartItemsResponse cartItemsResponse = new CartItemsResponse();
            List<CartItemResponse> cartItems = new ArrayList<>();
            List<Cart> cartItemsEntityList =  cartRepository.findByCustomerId(customerId);
            if(!cartItemsEntityList.isEmpty()) {
                for(Cart item : cartItemsEntityList){
                    Medicine medicineResponse = pharmaClient.getPharmacyMedicineById(item.getPharmaMedicineId());
                    CartItemResponse cartItemResponse =  CartItemResponse.builder()
                            .cartId(item.getCartId())
                            .customerId(item.getCustomerId())
                            .pharmaMedicineId(item.getPharmaMedicineId())
                            .quantity(item.getQuantity())
                            .status(item.getStatus())
                            .medicineName(medicineResponse.getMedicineName())
                            .medicineDescription(medicineResponse.getMedicineDescription())
                            .manfacturingCompany(medicineResponse.getManfacturingCompany())
                            .totalAmount(medicineResponse.getPrice() * item.getQuantity())
                            .build();
                    cartItems.add(cartItemResponse);
                }
                cartItemsResponse.setCartItems(cartItems);
            }
            return cartItemsResponse;
        }catch(Exception e) {
            throw new MediXpressException("Error while retrieving items from cart: " + e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

    }

    /**
     * This api delete a particular medicine/cart item for a particular customer
     * @param pharmaMedicineId
     * @param customerId
     */
    @Override
    @Transactional
    public void deleteByPharmaMedicineId(Long pharmaMedicineId, Long customerId) {
        try {
            Optional<Cart> cartItemsOptionalEntity = cartRepository.findByPharmaMedicineIdAndCustomerId(pharmaMedicineId,customerId);
            if (cartItemsOptionalEntity.isEmpty()) {
                throw new MediXpressException("Medicine not found to delete the details", HttpStatus.NOT_FOUND.value());
            } else {
                cartRepository.deleteByPharmaMedicineIdAndCustomerId(pharmaMedicineId, customerId);
            }
        } catch (Exception e) {
            throw new MediXpressException("Error while the deleting medicine from cart: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    /**
     * This method deletes all the items in the cart based on customerId
     * @param customerId
     */
    @Override
    @Transactional
    public void deleteAllCartItemsByCustomerId(Long customerId) {
        try {
            cartRepository.deleteByCustomerId(customerId);
        } catch (Exception e) {
            throw new MediXpressException("Error while deleting all the cart items: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
