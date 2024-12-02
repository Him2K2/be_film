package com.example.be_film.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
    @Size(min = 3,max=200,message = "name ngan sai")
    private String name;
    @Size(min = 3,max=200,message = "username ngan sai")
    @NotEmpty
    private String username;
    @Size(min = 3,max=30,message = "password ngan sai")
    @NotEmpty
    private String password;
    private String retypePassword;
    private String email;
    private int birth;
    private int budget ;


}
