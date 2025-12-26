package com.lynx.teste.controllers;

import com.lynx.teste.dtos.CustomersDto;
import com.lynx.teste.dtos.CustomersInsertDto;
import com.lynx.teste.services.CustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/customers")
public class CustomersControllers {

    @Autowired
    private CustomersService service;


    @PostMapping
    public ResponseEntity<CustomersDto>
        insert(@RequestBody CustomersInsertDto insertDto) {
        CustomersDto createdDto = service.insert(insertDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdDto.getId()).toUri();
        return ResponseEntity.created(uri).body(createdDto);

    }



}
