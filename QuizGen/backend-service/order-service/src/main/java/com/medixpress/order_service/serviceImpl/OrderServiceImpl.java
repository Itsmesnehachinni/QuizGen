package com.medixpress.order_service.serviceImpl;

import com.medixpress.order_service.feignClient.PharmaClient;
import com.medixpress.order_service.feignClient.UserClient;
import com.medixpress.order_service.constants.CartEnum;
import com.medixpress.order_service.constants.OrderEnum;
import com.medixpress.order_service.constants.PaymentEnum;
import com.medixpress.order_service.entity.*;
import com.medixpress.order_service.exception.MediXpressException;
import com.medixpress.order_service.repository.*;
import com.medixpress.order_service.request.StockUpdateRequest;
import com.medixpress.order_service.response.CustomerResponse;
import com.medixpress.order_service.response.Medicine;
import com.medixpress.order_service.response.OrderResponse;
import com.medixpress.order_service.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    OrderItemsRepository orderItemsRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    PharmaClient pharmaClient;
    @Autowired
    UserClient userClient;
    @Override
    @Transactional
    public void saveOrder(Long customerId) {
        try{
            Optional<List<Cart>> cartsOptionalEntity =
                    cartRepository.findByCustomerIdAndStatus(customerId, CartEnum.INPROGRESS.name());
            if(cartsOptionalEntity.isPresent()){
                List<Cart> cartList = cartsOptionalEntity.get();
                Orders orderEntity = Orders.builder()
                        .customerId(customerId)
                        .billAmount(getTotalBillAmountForOrder(cartList))
                        .orderStatus(OrderEnum.CREATED.name())
                        .paymentStatus(PaymentEnum.IN_PROGRESS.name())
                        .build();
                Orders savedOrder = ordersRepository.save(orderEntity);
                if(!ObjectUtils.isEmpty(savedOrder)){
                    cartList.forEach(cart -> {
                        OrderItems orderItemsEntity = OrderItems.builder()
                                .orderId(savedOrder.getOrderId())
                                .quantity(cart.getQuantity())
                                .pharmaMedicineId(cart.getPharmaMedicineId())
                                .build();
                        orderItemsRepository.save(orderItemsEntity);
                        updateCartStatusToComplete(cart);
                    });
                }
            }
        }catch(Exception e){
            throw new MediXpressException("Error occurred while placing the order : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    /**
     * feign call to pharma service
     * @param cartList
     * @return
     */
    private Double getTotalBillAmountForOrder(List<Cart> cartList) {
        return cartList.stream().map(cart -> {
                    Medicine medicine = pharmaClient.getPharmacyMedicineById(cart.getPharmaMedicineId());
                    if(medicine != null)
                        return medicine.getPrice() * cart.getQuantity();
                    else
                        return 0.0;
                })
                .reduce(0.0, Double::sum);
    }

    /**
     * feign call to user service to get customer using id
     * @param customerId
     * @return
     */
    @Override
    public OrderResponse getOrdersByCustomerId(Long customerId) {
        CustomerResponse customer = userClient.getCustomerById(customerId);
        Optional<Orders> order = ordersRepository.findByCustomerIdAndPaymentStatusAndOrderStatus(
                customerId,PaymentEnum.IN_PROGRESS.name(),OrderEnum.CREATED.name());
        if(order.isPresent()) {
            List<OrderItems> orderItemList = orderItemsRepository.findByOrderId(order.get().getOrderId());
            if (!ObjectUtils.isEmpty(customer) && !orderItemList.isEmpty())
                return constructOrderResponse(customer, order.get(), orderItemList);
        }
        return null;
    }

    @Override
    public void updateOrderStatusForOrderId(Long orderId) {
        Optional<Orders> orders = ordersRepository.findByOrderId(orderId);
        if(orders.isPresent()){
            Orders updatedOrder = Orders.builder()
                    .orderId(orders.get().getOrderId())
                    .customerId(orders.get().getCustomerId())
                    .billAmount(orders.get().getBillAmount())
                    .orderStatus(OrderEnum.ORDERED.name())
                    .paymentStatus(PaymentEnum.SUCCESSFUL.name())
                    .build();
            ordersRepository.save(updatedOrder);
            List<OrderItems> orderItemsList = orderItemsRepository.findByOrderId(orderId);
            Map<Long,Integer> stockByPharmaMedicineId = new HashMap<>();
            orderItemsList.forEach(orderItems -> {
                stockByPharmaMedicineId.put(orderItems.getPharmaMedicineId(),orderItems.getQuantity());
            });
            StockUpdateRequest stockUpdateRequest = StockUpdateRequest.builder()
                    .updateStockForPharmaMedicineId(stockByPharmaMedicineId)
                    .build();
            pharmaClient.updateStockByPharmaMedicineId(stockUpdateRequest);
        }
    }

    /**
     * feign call to pharma service to get pharmacy medicine
     * @param customer
     * @param order
     * @param orderItemList
     * @return
     */
    private OrderResponse constructOrderResponse(CustomerResponse customer, Orders order, List<OrderItems> orderItemList) {
        List<OrderResponse.OrderItemResponse> orderItemResponseList = orderItemList.stream().map(orderItems -> {
            Medicine medicineResponse = pharmaClient.getPharmacyMedicineById(orderItems.getPharmaMedicineId());
            return OrderResponse.OrderItemResponse.builder()
                    .medicineName(medicineResponse.getMedicineName())
                    .pricePerItem(medicineResponse.getPrice())
                    .quantity(orderItems.getQuantity())
                    .totalMedicineAmount(medicineResponse.getPrice() * orderItems.getQuantity())
                    .build();
        }).collect(Collectors.toList());
        return OrderResponse.builder()
                .customer(customer)
                .totalBillAmount(order.getBillAmount())
                .orderItemResponseList(orderItemResponseList)
                .orderId(order.getOrderId())
                .build();
    }

    private void updateCartStatusToComplete(Cart cart) {
        Cart cartEntity = Cart.builder()
                .cartId(cart.getCartId())
                .customerId(cart.getCustomerId())
                .pharmaMedicineId(cart.getPharmaMedicineId())
                .quantity(cart.getQuantity())
                .status(CartEnum.COMPLETED.name())
                .build();
        cartRepository.save(cartEntity);
    }
}
