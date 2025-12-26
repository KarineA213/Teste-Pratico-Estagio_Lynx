package com.lynx.teste.entities;

import com.lynx.teste.enums.Status;
import jakarta.persistence.*;
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
    private Customers customers;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime createdAt;

    @OneToMany( mappedBy = "order")
    private List<Payments> payments = new ArrayList<>();

    @OneToMany(mappedBy = "order")
    private List<OrderItems> items = new ArrayList<>();

    private BigDecimal totalAmount;


    public BigDecimal CalculateTotalAmount() {

        this.totalAmount = items.stream()
                .map(item -> item.getProduct().getPriceCents()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalAmount;



        //NÃO DEU CERTO, RETORNA NULL NO BANCO
//       this.totalAmount= items.stream().map(item ->{
//       BigDecimal price = item.getUnitPriceCents();
//       BigDecimal qtd = BigDecimal.valueOf(item.getQuantity());
//       return price.multiply(qtd);
//       })
//        .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        return totalAmount;

    }





}
