package com.example.be_film.repositories;

import com.example.be_film.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre,Long> {

}
