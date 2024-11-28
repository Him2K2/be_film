package com.example.be_film.service;

import com.example.be_film.dtos.PaymentDTO;
import com.example.be_film.exceptions.DataNotFoundException;
import com.example.be_film.responses.PaymentResponse;

import java.util.List;

public interface IPaymentService {

    Boolean createPaymentAndChangeMount(PaymentDTO paymentDTO) throws DataNotFoundException;
    PaymentResponse getPayment(Long id);
//    PaymentResponse updatePayment(Long id,PaymentDTO paymentDTO);
//    void deletePayment (Long id);
    List<PaymentResponse> getPaymentsByUserId(Long userId);


}
