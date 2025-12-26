package com.lynx.teste.services;

import com.lynx.teste.dtos.ProductsDto;
import com.lynx.teste.entities.Products;
import com.lynx.teste.repositories.ProductsRepository;
import com.lynx.teste.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductsService {

    @Autowired
    ProductsRepository repository;


    @Transactional
    public ProductsDto findById(Long id) {
       Products entity = repository.findById(id)
               .orElseThrow(
                       () -> new ResourceNotFoundException("Id não encontrado para este produto"));
                       return new ProductsDto(entity);
    }

    @Transactional
    public Page<ProductsDto> findByName(String name,  Pageable pageable) {
        Page<Products> result = repository.searchProductsByName(name, pageable);
    return result.map(x ->  new ProductsDto(x));
    }


    @Transactional
    public Page<ProductsDto> findAll(Pageable pageable) {
        Page<Products> entity = repository.findAll(pageable);
        return entity.map(product -> new ProductsDto(product));
    }

    @Transactional
    public ProductsDto insert(ProductsDto dto) {
        Products product = new Products();
        copyToEntity(dto, product);
        product = repository.save(product);
        return new ProductsDto(product);

    }


    public void copyToEntity(ProductsDto dto, Products entity) {
        entity.setName(dto.getName());
        entity.setCategory(dto.getCategory());
        entity.setPriceCents(dto.getPriceCents());
        entity.setActive(dto.isActive());
    }

}
