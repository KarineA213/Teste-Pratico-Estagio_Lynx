package com.lynx.teste.dtos;

import com.lynx.teste.entities.OrderItems;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderSimplifiedDto {

    private Long orderId;
    private Long productId;
    private Long quantity;
    private BigDecimal totalAmount;
    private List<OrderItemsDto> items;
    private String productName;
    private String customerName;
    private LocalDateTime createdAt;




    public OrderSimplifiedDto(OrderSimplifiedDto entity) {
        orderId = entity.getOrderId();
        customerName = entity.getCustomerName();
        this.items = (entity.getItems() == null)
                ? new ArrayList<>()
                : entity.getItems().stream().map(OrderItemsDto::new).toList();

        productId = entity.getProductId();
        productName = entity.getProductName();
        quantity = entity.getQuantity();
        totalAmount = entity.getTotalAmount();
        createdAt = entity.getCreatedAt();

    }


}
