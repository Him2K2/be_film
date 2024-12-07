package com.example.be_film.controller;

import com.example.be_film.dtos.GenreDTO;
import com.example.be_film.model.Genre;
import com.example.be_film.service.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("http://localhost:3000/")
@RequestMapping("/api/v1/genre")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @PostMapping("")
    public ResponseEntity<GenreDTO> createGenre(@Valid @RequestBody GenreDTO genreDTO){

        Genre genre = new Genre();
        genre.setGenreName(genreDTO.getGenreName());

        Genre saveGenre = genreService.createGenre(genre);
        GenreDTO responseDTO = new GenreDTO(saveGenre.getGenreName());

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);

    }

    @GetMapping("")
    public ResponseEntity<List<Genre>>getAllGenre(){
        List<Genre> genres = genreService.getAllGenre();

        return ResponseEntity.ok(genres);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateGenres(@PathVariable Long id,@RequestBody GenreDTO genreDTO){
        Genre genre = new Genre();
        genre.setGenreName(genreDTO.getGenreName());
        genreService.updateGenre(id,genre);

        return ResponseEntity.ok("updated");


    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGenre(@PathVariable Long id){
        genreService.deleteGenre(id);
        return ResponseEntity.ok("delete genre with id "+ id);
    }
}
