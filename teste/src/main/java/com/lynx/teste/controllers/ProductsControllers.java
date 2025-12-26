package com.lynx.teste.controllers;

import com.lynx.teste.dtos.ProductsDto;
import com.lynx.teste.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.error.DefaultErrorAttributes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/products")
public class ProductsControllers {

    @Autowired
    private ProductsService productsService;
    @Autowired
    private DefaultErrorAttributes defaultErrorAttributes;


    @GetMapping("/{id}")
    public ResponseEntity<ProductsDto>
    getProduct(@PathVariable("id") Long id) {
        ProductsDto productsDto = productsService.findById(id);
        return ResponseEntity.ok(productsDto);
    }

    @GetMapping
    public ResponseEntity<Page<ProductsDto>>getAllProducts
            (@RequestParam(name = "name", defaultValue = "") String name, Pageable pageable)
    {
        Page<ProductsDto> productsPage;

        if (name.trim().isBlank()){
            productsPage = productsService.findAll(pageable);
        }
        else{
            productsPage = productsService.findByName(name, pageable);

        }

        return ResponseEntity.ok(productsPage);
    }

    @PostMapping
    public ResponseEntity<ProductsDto> insert (@RequestBody ProductsDto dto){
        ProductsDto productsDto = productsService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(productsDto.getId()).toUri();
        return ResponseEntity.created(uri).body(productsDto);
    }
}
