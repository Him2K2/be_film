package com.example.be_film.model.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class KeyGenreFilm implements Serializable {

    @Column(name="genreid")
    private Long genreId;
    @Column(name="filmid")
    private  Long filmId;

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public KeyGenreFilm(){};

    public KeyGenreFilm(Long filmId , Long genreId){
        this.filmId = filmId;
        this.genreId = genreId;
    }
}
