package com.lynx.teste.dtos;

import com.lynx.teste.entities.Customers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CustomersDto {


    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdAt;



    public CustomersDto(Customers entity) {
        id = entity.getId();
        name = entity.getName();
        email = entity.getEmail();
        createdAt = entity.getCreatedAt();
    }

    public Long getId() {
        return id;
    }
}
