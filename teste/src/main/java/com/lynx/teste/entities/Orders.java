package com.lynx.teste.entities;

import com.lynx.teste.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="customer_id")
    @NotNull
    private Customers customers;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime createdAt;

    @OneToMany( mappedBy = "order")
    private List<Payments> payments = new ArrayList<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @NotEmpty
    private List<OrderItems> items = new ArrayList<>();

    private BigDecimal totalAmount;



    public BigDecimal CalculateTotalAmount() {

        this.totalAmount = items.stream()
                .map(item -> item.getProduct().getPriceCents()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalAmount;


    }


    @PrePersist
    @PreUpdate
    public void prePersist() {
        CalculateTotalAmount();
    }





}
