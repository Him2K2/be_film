package com.example.be_film.repositories;

import com.example.be_film.model.Film;
import com.example.be_film.model.FilmGenre;
import com.example.be_film.model.keys.KeyGenreFilm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmGenreRepository extends JpaRepository<FilmGenre, KeyGenreFilm> {
    void deleteAllByFilms(Film film);
}
