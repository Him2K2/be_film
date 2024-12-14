package com.example.be_film.service;

import com.example.be_film.dtos.PaymentDTO;
import com.example.be_film.exceptions.DataNotFoundException;
import com.example.be_film.model.Film;
import com.example.be_film.model.Payment;
import com.example.be_film.model.User;
import com.example.be_film.repositories.FilmRepository;
import com.example.be_film.repositories.PaymentRepository;
import com.example.be_film.repositories.UserRepository;
import com.example.be_film.responses.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor


public class PaymentService implements IPaymentService{
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final FilmRepository filmRepository;
    ModelMapper modelMapper;
    @Override
    public Boolean createPaymentAndChangeMount(PaymentDTO paymentDTO) throws DataNotFoundException {
        User user = userRepository.findByUsername(paymentDTO.getUsername()).orElseThrow(()
                -> new DataNotFoundException("can't find userid " + paymentDTO.getUsername()));
        Film film = filmRepository.findById(paymentDTO.getFilmId()).orElseThrow(()
                -> new DataNotFoundException("can't find userid " + paymentDTO.getFilmId()));
        // tru tien
        user.setBudget(user.getBudget() - paymentDTO.getAmount());
        //luu lai ban ghi vua tru tien
        userRepository.save(user);
        Payment payment = new Payment();
        payment.setAmount(paymentDTO.getAmount());
        payment.setFilm(film);
        payment.setUser(user);
        payment.setStatus(paymentDTO.getStatus());

        paymentRepository.save(payment);

        film.setStatus(paymentDTO.getStatus());
        filmRepository.save(film);
        return true;
    }

    @Override
    public PaymentResponse getPayment(Long id) {
        return null;
    }


    @Override
    public List<PaymentResponse> getPaymentsByUserId(Long userId) {
        return null;
    }
}
