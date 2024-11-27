package com.example.be_film.repositories;

import com.example.be_film.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    List<Payment> findByUserId(Long userid);
}
