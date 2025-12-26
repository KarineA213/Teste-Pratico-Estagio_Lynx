package com.lynx.teste.dtos;

import com.lynx.teste.entities.Orders;
import com.lynx.teste.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
public class OrdersDto {


    private Long id;

    private Long customerId;

    private String customerName;

    private String customerEmail;

    private Status status;

    private LocalDateTime createdAt;

    private List<PaymentsDto> payments;

    private List<OrderItemsDto> items;

    private BigDecimal totalAmount;

    public OrdersDto(Long id, Long customerId, String customerName, String customerEmail, Status status, LocalDateTime createdAt, List<PaymentsDto> payments, List<OrderItemsDto> items, BigDecimal totalAmount) {
        this.id = id;
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.status = status;
        this.createdAt = createdAt;
        this.payments = payments;
        this.items = items;
        this.totalAmount = totalAmount;
    }

    public OrdersDto(Orders entity) {
        id = entity.getId();
        customerName = entity.getCustomers().getName();
        customerEmail = entity.getCustomers().getEmail();
        status = entity.getStatus();
        createdAt = entity.getCreatedAt();
        totalAmount = entity.getTotalAmount();

//
//        this.payments = entity.getPayments().stream()
//                .map(x -> new PaymentsDto(x))
//                .toList();

        this.payments = (entity.getPayments() == null) ? new ArrayList<>() :
                entity.getPayments().stream()
                .map(x -> new PaymentsDto(x)).toList();

        this.items = (entity.getItems() == null)
                ? new ArrayList<>()
                : entity.getItems().stream().map(OrderItemsDto::new).toList();
    }

//        items = entity.getItems().stream()
//                .map(x -> new OrderItemsDto(x))
//                .toList();}
//



}
