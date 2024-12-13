package com.example.be_film.model;

import com.example.be_film.model.keys.KeyGenreFilm;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "genre_film")
public class FilmGenre {
    @EmbeddedId
    private KeyGenreFilm keys;

    @ManyToOne
    @JoinColumn(name = "filmid", insertable = false, updatable = false)
    private Film films;

    @ManyToOne
    @JoinColumn(name = "genreid", insertable = false, updatable = false)
    private Genre genres;

    public FilmGenre(KeyGenreFilm keys) {
        this.keys = keys;
    }
}
