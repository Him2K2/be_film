package com.example.be_film.repositories;

import com.example.be_film.model.Payment;
import com.example.be_film.model.WatchList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WatchListRepository extends JpaRepository<WatchList,Long> {

    List<WatchList> findByUserId(Long userid);
    List<WatchList> findByFilmId(Long filmid);
    List<WatchList> findByUserIdOrderByCreatedAtDesc(Long userid);

}
