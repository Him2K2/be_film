package com.example.be_film.controller;

import com.example.be_film.dtos.FilmDTO;
import com.example.be_film.exceptions.DataNotFoundException;
import com.example.be_film.model.Film;
import com.example.be_film.service.FilmService;
import com.example.be_film.service.iFilmService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000/")
@RequestMapping("/api/v1/films")
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @PostMapping
    public ResponseEntity<Film> createFilm(@RequestBody FilmDTO filmDTO) {
        Film createdFilm = filmService.createFilm(filmDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFilm);
    }
    @GetMapping("/search")
    public ResponseEntity<List<Film>> searchFilms(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "5") int less
    ) {
        List<Film> films = filmService.searchFilms(keyword, less);
        return ResponseEntity.ok(films);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable long id) {
        try {
            Film film = filmService.getFilmById(id);
            return ResponseEntity.ok(film);
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<Page<Film>> getAllFilms(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Film> films = filmService.getAllFilm(pageRequest);
        return ResponseEntity.ok(films);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Film> updateFilm(@PathVariable long id, @Valid @RequestBody FilmDTO filmDTO) {
        try {
            Film updatedFilm = filmService.updateFilm(id, filmDTO);
            return ResponseEntity.ok(updatedFilm);
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable long id) throws DataNotFoundException {
        filmService.deleteFilm(id);
        return ResponseEntity.noContent().build();
    }

}

