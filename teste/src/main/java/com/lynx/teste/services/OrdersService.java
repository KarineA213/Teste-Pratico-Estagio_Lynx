package com.lynx.teste.services;

import com.lynx.teste.dtos.OrderItemsDto;
import com.lynx.teste.dtos.OrdersDto;
import com.lynx.teste.dtos.OrdersInsertDto;
import com.lynx.teste.entities.Customers;
import com.lynx.teste.entities.OrderItems;
import com.lynx.teste.entities.Orders;
import com.lynx.teste.entities.Products;
import com.lynx.teste.enums.Status;
import com.lynx.teste.repositories.CustomersRepository;
import com.lynx.teste.repositories.OrdersRepository;
import com.lynx.teste.repositories.ProductsRepository;
import com.lynx.teste.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrdersService {


    @Autowired
    OrdersRepository repository;
    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private CustomersRepository customersRepository;

    @Transactional(readOnly = true)
    public OrdersDto findById(Long id) {
        Orders entity = repository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("pedido com o id : " + id + " não foi encontrado"));
                    return new OrdersDto(entity);

    }

    @Transactional
    public OrdersDto insert(OrdersInsertDto dto) {

        Orders entity = new Orders();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new OrdersDto(entity);
    }


    @Transactional(readOnly = true)
    public Page<OrdersDto> findAll(Pageable pageable) {
        Page<Orders> entity = repository.findAll(pageable);
        return entity.map(OrdersDto::new);
    }





    private void copyDtoToEntity(OrdersInsertDto dto, Orders entity) {

        Customers customers = customersRepository.findById(dto.getCustomerId())
                        .orElseThrow(() -> new ResourceNotFoundException("cliente com o id : " + dto.getId() + "não encontrado"));
        entity.setCustomers(customers);
        entity.setId(dto.getId());
        entity.setStatus(Status.NEW);
        entity.setCreatedAt(LocalDateTime.now());

        List<OrderItems> items = new ArrayList<>();

        for (OrderItemsDto itemsDto : dto.getItems()) {

            Products product = productsRepository.findById(itemsDto.getProductId())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("produto não pode ser encontrado!"));


            OrderItems orderItems = new OrderItems();

            if (product.isActive()) {
            orderItems.setProduct(product);
            orderItems.setQuantity(itemsDto.getQuantity());
            orderItems.setUnitPriceCents(product.getPriceCents());
            orderItems.setOrder(entity);
            items.add(orderItems);

            }else {
                throw  new ResourceNotFoundException("Produto não está disponível no momento");
            }

        }

        entity.setItems(items);
        entity.getTotalAmount();
    }



}
