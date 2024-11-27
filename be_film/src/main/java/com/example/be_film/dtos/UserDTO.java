package com.example.be_film.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String name;
    private String username;

    private String password;
    private String email;
    @JsonProperty("date_of_birth")
    private int birth;
    private int budget;
}
