package com.example.be_film.service;

import com.example.be_film.dtos.FilmDTO;
import com.example.be_film.exceptions.DataNotFoundException;
import com.example.be_film.model.Film;
import com.example.be_film.repositories.FilmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FilmService implements iFilmService{
    @Autowired
    FilmRepository filmRepository;
    @Override
    public Film createFilm(FilmDTO filmDTO) {
        Film newFilm = Film.builder()
                .filmName(filmDTO.getFilmname())
                .imgFilm(filmDTO.getImgFilm())
                .urlFilm(filmDTO.getUrlFilm())
                .duration(filmDTO.getDuration())
                .status(filmDTO.getStatus())
                .caption(filmDTO.getCaption())
                .description(filmDTO.getDescription())
                .build();
        return filmRepository.save(newFilm);
    }

    @Override
    public Film getFilmById(long id) throws DataNotFoundException {
        return filmRepository.findById(id).orElseThrow(()-> new DataNotFoundException("can't find y id "+id));
    }

    @Override
    public Page<Film> getAllFilm(PageRequest pageRequest) {

        return filmRepository.findAll(pageRequest);
    }

    @Override
    public Film updateFilm(long id, FilmDTO filmDTO) throws Exception{
       Film existingFilm = getFilmById(id);
       if(existingFilm!=null){
           existingFilm.setFilmName(filmDTO.getFilmname());
           existingFilm.setImgFilm(filmDTO.getImgFilm());
           existingFilm.setUrlFilm(filmDTO.getUrlFilm());
           existingFilm.setDuration(filmDTO.getDuration());
           existingFilm.setCaption(filmDTO.getCaption());
           existingFilm.setStatus(filmDTO.getStatus());
           existingFilm.setDescription(filmDTO.getDescription());
           return filmRepository.save(existingFilm);
       }



        return null;
    }

    @Override
    public void deleteFilm(long id) {
        filmRepository.findById(id);
        filmRepository.deleteById(id);

    }

    @Override
    public boolean existByName(String nameFilm) {
        return filmRepository.existsByFilmName(nameFilm);
    }
}
