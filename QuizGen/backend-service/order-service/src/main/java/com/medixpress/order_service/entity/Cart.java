package com.medixpress.order_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CART")
public class Cart {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CART_ID")
    private Long cartId;

    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Column(name = "PHARMA_MEDICINE_ID")
    private Long pharmaMedicineId;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "STATUS")
    private String status;


}
