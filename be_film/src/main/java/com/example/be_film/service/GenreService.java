package com.example.be_film.service;

import com.example.be_film.model.Genre;
import com.example.be_film.repositories.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class GenreService implements IGenreService{
    private final GenreRepository genreRepository;
    public GenreService(GenreRepository genreRepository){
        this.genreRepository = genreRepository;;
    };
    @Override
    public Genre createGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public Genre getGenreById(long id) {
        return genreRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Genre not found"));
    }

    @Override
    public List<Genre> getAllGenre() {
        return genreRepository.findAll();
    }

    @Override
    public Genre updateGenre(long genreId, Genre genre) {
        Genre existingGenre = getGenreById((genreId));
        existingGenre.setGenreName((genre.getGenreName()));
        return genreRepository.save(existingGenre);
    }

    @Override
    public void deleteGenre(long id) {
        if (!genreRepository.existsById(id)) {
            throw new RuntimeException("Genre not found with ID: " + id);
        }
        genreRepository.deleteById(id);
    }

}
