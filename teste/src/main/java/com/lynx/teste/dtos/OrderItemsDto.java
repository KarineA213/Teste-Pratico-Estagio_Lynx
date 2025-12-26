package com.lynx.teste.dtos;

import com.lynx.teste.entities.OrderItems;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderItemsDto {



    private Long id;
//    private Orders order; //o json entrou em looping
    private String productName;
    private Long productId;

    private int quantity;
    private BigDecimal unitPriceCents;



    public OrderItemsDto(OrderItems entity) {
        id = entity.getId();
        productName = entity.getProduct().getName();
        quantity = entity.getQuantity();
        unitPriceCents = entity.getUnitPriceCents();
    }


}
