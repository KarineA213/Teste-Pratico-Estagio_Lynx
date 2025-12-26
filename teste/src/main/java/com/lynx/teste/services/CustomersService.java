package com.lynx.teste.services;


import com.lynx.teste.dtos.CustomersDto;
import com.lynx.teste.dtos.CustomersInsertDto;
import com.lynx.teste.entities.Customers;
import com.lynx.teste.repositories.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CustomersService {

    @Autowired
    private CustomersRepository customersRepository;


    @Transactional
    public CustomersDto insert(CustomersInsertDto insertDto){
        Customers customers = new Customers();
        copyToEntityInsert(insertDto, customers);
        customers = customersRepository.save(customers);
        return new CustomersDto(customers);


    }



    private void copyToEntityInsert(CustomersInsertDto insertDto, Customers entity){
        entity.setName(insertDto.getName());
        entity.setEmail(insertDto.getEmail());
        entity.setCreatedAt(LocalDateTime.now());
    }

}
