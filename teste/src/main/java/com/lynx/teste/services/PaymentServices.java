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
    public PaymentsDto insert(PaymentsDto payments) {
        Payments newPayments = new Payments();
        copytoEntity(new PaymentsDto(), newPayments);
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
                }else if (total.compareTo(orders.getTotalAmount()) <= 0){
                    orders.setStatus(Status.ON_WAIT);
                }else{
                    throw new PaymentError("pagamento acima do valor Total, reveja o valor de pagamento");
                }



//                BigDecimal pagamentos = orders.getTotalAmount();
//
//                if (paymentsDto.getAmountCents().compareTo(pagamentos)==0) {
//
//                    entity.setPaidAt(LocalDateTime.now());
//                    orders.setStatus(Status.PAID);
//
//
//                } else if (pagamentos.compareTo(paymentsDto.getAmountCents()) > 0) {
//
//                    orders.setStatus(Status.ON_WAIT);
//
//                }else {
//                    //Add error porque o valor de pagamento foi maior que o total da compra.
//                }


        }

        }

    }




