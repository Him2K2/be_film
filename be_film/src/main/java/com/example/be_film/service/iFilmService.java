package com.example.be_film.service;

import com.example.be_film.dtos.FilmDTO;
import com.example.be_film.exceptions.DataNotFoundException;
import com.example.be_film.model.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface iFilmService
{
    public Film createFilm(FilmDTO filmDTO);
    Film getFilmById(long id) throws DataNotFoundException;
    Page<Film> getAllFilm(PageRequest pageRequest);
    Film updateFilm(long id,FilmDTO filmDTO) throws Exception;
    void deleteFilm(long id) throws DataNotFoundException;
    boolean existByName(String nameFilm);
}
