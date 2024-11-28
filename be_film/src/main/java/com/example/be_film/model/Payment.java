package com.example.be_film.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="payment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int amount;
    @Column(name="payment_date")
    private LocalDateTime paymentDate;
    @PrePersist
    public void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        paymentDate = now;
    }
    private String status;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

    @ManyToOne
    @JoinColumn(name = "filmid")
    private Film film;

}
