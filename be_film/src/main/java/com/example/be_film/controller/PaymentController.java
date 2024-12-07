package com.example.be_film.controller;

import com.example.be_film.dtos.FilmDTO;
import com.example.be_film.dtos.PaymentDTO;
import com.example.be_film.exceptions.DataNotFoundException;
import com.example.be_film.model.Film;
import com.example.be_film.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:3000/")
@RequestMapping("/api/v1/payment")
public class PaymentController {
    @Autowired
    PaymentService paymentService;
    @PostMapping("/create")
    public ResponseEntity<Boolean> createPaymentAndChangeMountUser(@RequestBody PaymentDTO paymentDTO) throws DataNotFoundException {
        Boolean createdFilm = paymentService.createPaymentAndChangeMount(paymentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFilm);
    }

}
