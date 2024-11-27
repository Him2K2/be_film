package com.example.be_film.repositories;

import com.example.be_film.model.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Reviews,Long> {
}
