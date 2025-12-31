package com.lynx.teste.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CustomersInsertDto {


    private String name;
    private String email;


    public CustomersInsertDto(CustomersDto entity) {
        name = entity.getName();
        email = entity.getEmail();
    }

}

