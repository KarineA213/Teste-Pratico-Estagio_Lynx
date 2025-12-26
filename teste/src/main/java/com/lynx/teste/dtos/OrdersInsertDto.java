package com.lynx.teste.dtos;

import com.lynx.teste.entities.Orders;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrdersInsertDto {

    private Long Id;
    private Long customerId;
    private List<OrderItemsDto> items;


    public OrdersInsertDto(Orders entity) {

        Id = entity.getId();
        customerId = entity.getCustomers().getId();
        items = entity.getItems().stream()
                .map(x -> new OrderItemsDto(x))
                .toList();}




}



