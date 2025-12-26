package com.lynx.teste.repositories;

import com.lynx.teste.entities.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Products, Long> {
    Page<Products> searchProductsByName(String name,  Pageable pageable);
}
