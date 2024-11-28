package com.example.be_film.service;

import com.example.be_film.model.Genre;

import java.util.List;

public interface IGenreService {
    Genre createGenre(Genre genre);

    Genre getGenreById(long id);

    List<Genre> getAllGenre();

    Genre updateGenre(long genreId,Genre genre);

    void deleteGenre(long id);


}
