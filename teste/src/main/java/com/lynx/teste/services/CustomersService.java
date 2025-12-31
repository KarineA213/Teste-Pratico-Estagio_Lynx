package com.lynx.teste.services;


import com.lynx.teste.dtos.CustomersDto;
import com.lynx.teste.dtos.CustomersInsertDto;
import com.lynx.teste.entities.Customers;
import com.lynx.teste.repositories.CustomersRepository;
import com.lynx.teste.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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



    @Transactional(readOnly = true)
    public Page<CustomersDto> findAll(Pageable pageable) {
        Page<Customers> customersPage = customersRepository.findAll(pageable);
        return  customersPage.map(x -> new CustomersDto(x));

    }

    @Transactional
    public CustomersDto findById(Long id) {
        Customers customers = customersRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Cliente não encontrado"));;
                return new CustomersDto(customers);
    }




    private void copyToEntityInsert(CustomersInsertDto insertDto, Customers entity){
        entity.setName(insertDto.getName());
        entity.setEmail(insertDto.getEmail());
        entity.setCreatedAt(LocalDateTime.now());
    }
}
