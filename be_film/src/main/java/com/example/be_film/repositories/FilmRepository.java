package com.example.be_film.repositories;

import com.example.be_film.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
    boolean existsByFilmName(String filmName);

        @Query("SELECT f FROM Film f WHERE f.filmName LIKE %:keyword%")
        List<Film> findTopByKeyword(@Param("keyword") String keyword, Pageable pageable);


    Page<Film> findAll(Pageable pageable); // Phân trang
}
