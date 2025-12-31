package com.lynx.teste.dtos;

import com.lynx.teste.entities.Orders;
import com.lynx.teste.entities.Payments;
import com.lynx.teste.enums.Method;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor

public class PaymentsDto {


    private Long id;

    private Long orderId;

    private Method method;

    private BigDecimal amountCents;

    private LocalDateTime paidAt;


    public PaymentsDto(Payments entity) {
        id = entity.getId();
        orderId = entity.getOrder().getId();
        method = entity.getMethod();
        amountCents = entity.getAmountCents();
        paidAt = entity.getPaidAt();


    }

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Method getMethod() {
        return method;
    }

    public BigDecimal getAmountCents() {
        return amountCents;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public void setAmountCents(BigDecimal amountCents) {
        this.amountCents = amountCents;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }
}
