package com.lynx.teste.controllers;

import com.lynx.teste.dtos.OrdersDto;
import com.lynx.teste.dtos.OrdersInsertDto;
import com.lynx.teste.services.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/orders")
public class OrdersControllers {

    @Autowired
    OrdersService ordersService;


    @GetMapping("/{id}")
    public ResponseEntity<OrdersDto> findById( @PathVariable Long id){
        OrdersDto dto = ordersService.findById(id);
        return ResponseEntity.ok(dto);

    }

    @GetMapping
    public ResponseEntity<Page<OrdersDto>> findAll(Pageable pageable){
        Page<OrdersDto> entity = ordersService.findAll(pageable);
        return ResponseEntity.ok(entity);
    }


    @PostMapping
    public ResponseEntity<OrdersDto> insert(@RequestBody OrdersInsertDto dto)
    {
     OrdersDto createdOrderDto = ordersService.insert(dto);
     URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
             .buildAndExpand(createdOrderDto.getId()).toUri();
     return ResponseEntity.created(uri).build();

    }











}
