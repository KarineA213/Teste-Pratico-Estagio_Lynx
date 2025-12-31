package com.lynx.teste.controllers;

import com.lynx.teste.dtos.CustomersDto;
import com.lynx.teste.dtos.CustomersInsertDto;
import com.lynx.teste.services.CustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*") // Permite que o seu front-end acesse a API
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

    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomersDto> findById(@PathVariable Long id) {
        CustomersDto customersDto = service.findById(id);
        return ResponseEntity.ok().body(customersDto);
    }

    @GetMapping
    public ResponseEntity<Page<CustomersDto>> findAll(Pageable pageable) {
        Page<CustomersDto> page = service.findAll(pageable);
        return ResponseEntity.ok(page);
    }
}




