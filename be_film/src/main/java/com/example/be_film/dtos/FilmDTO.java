package com.example.be_film.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilmDTO {
    @Size(min = 3,max=200,message = "ten phim ngan sai")
    private String filmname;
    @JsonProperty("img_film")
    private String imgFilm;
    @JsonProperty("url_film")
    private String urlFilm;
    private int duration;
    private String status;
    @JsonProperty("average_rating")
    private int averageRating;
    private String caption;
    private String description;
}
