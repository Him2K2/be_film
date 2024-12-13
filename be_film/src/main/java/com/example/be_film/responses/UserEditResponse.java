package com.example.be_film.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;

public class UserEditResponse {
    private Long id;

    @JsonProperty("username")
    private String userName;

    @JsonProperty("name")
    private String name;

    @JsonProperty("budget")
    private int budget;

    @JsonProperty("date_of_birth")
    private int DateOfBirth; // Change to LocalDate for Date of Birth

}
