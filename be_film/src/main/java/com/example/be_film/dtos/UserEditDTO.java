package com.example.be_film.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEditDTO {
    @Size(min = 3,max=200,message = "name ngan sai")
    private String name;
    @Size(min = 3,max=200,message = "username ngan sai")
    private String username;
    private int birth;
    private int budget ;

}
