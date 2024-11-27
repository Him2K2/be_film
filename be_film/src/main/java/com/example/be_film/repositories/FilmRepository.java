package com.example.be_film.repositories;

import com.example.be_film.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
    boolean existsByFilmName(String filmName);
    Page<Film> findAll(Pageable pageable); // Phân trang
}
