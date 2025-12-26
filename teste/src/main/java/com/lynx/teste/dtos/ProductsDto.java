package com.lynx.teste.dtos;

import com.lynx.teste.entities.Products;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProductsDto {

    private Long id;
    private String name;
    private String category;
    private BigDecimal priceCents;
    private boolean active;



    public ProductsDto(Products entity) {
        id = entity.getId();
        name = entity.getName();
        category = entity.getCategory();
        priceCents = entity.getPriceCents();
        active = entity.isActive();
    }




}
