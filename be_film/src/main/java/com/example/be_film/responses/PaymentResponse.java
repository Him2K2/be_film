package com.example.be_film.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private Long id;
    private Long userId;
    private String username;
    private int amount;
    private String status;
    private String filmId;

}
