package com.lynx.teste.repositories;

import com.lynx.teste.entities.Payments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentsRepository extends JpaRepository<Payments, Integer> {

}
