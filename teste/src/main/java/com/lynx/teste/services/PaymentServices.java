package com.lynx.teste.services;

import com.lynx.teste.dtos.PaymentsDto;
import com.lynx.teste.entities.Orders;
import com.lynx.teste.entities.Payments;
import com.lynx.teste.enums.Status;
import com.lynx.teste.repositories.OrdersRepository;
import com.lynx.teste.repositories.PaymentsRepository;
import com.lynx.teste.services.exceptions.PaymentError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
public class PaymentServices {

    @Autowired
    PaymentsRepository paymentsRepository;
    @Autowired
    private OrdersRepository ordersRepository;


    @Transactional
    public PaymentsDto insert(PaymentsDto paymentsDto) {
        Payments newPayments = new Payments();
        copytoEntity(paymentsDto, newPayments);
        paymentsRepository.save(newPayments);
        return new PaymentsDto(newPayments);
    }


    public void copytoEntity(PaymentsDto paymentsDto, Payments entity) {

        entity.setId(paymentsDto.getId());
        entity.setMethod(paymentsDto.getMethod());
        entity.setAmountCents(paymentsDto.getAmountCents());

        if(paymentsDto.getOrderId() != null){
                Orders  orders = ordersRepository.getReferenceById(paymentsDto.getOrderId());
                entity.setOrder(orders);

                BigDecimal pagamentos = orders.getPayments().stream()
                        .map(Payments::getAmountCents)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal total = pagamentos.add(paymentsDto.getAmountCents());

                if(total.compareTo(orders.getTotalAmount()) >= 0){
                    orders.setStatus(Status.PAID);
                }else
                    orders.setStatus(Status.ON_WAIT);


        }

        }

    }




