package com.example.be_film.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="film")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="filmname")
    private String filmName;
    @Column(name="img_film")
    private String imgFilm;
    @Column(name="img_bannerfilm")
    private String imgBannerFilm;
    @Column(name="url_film")
    private String urlFilm;
    private int duration;
    private String status;
    private String caption;
    @Column(name="average_rating")
    private int averageRating;
    private String description;

    @Column(name="created_at")
    private LocalDateTime createdAt;
    @Column(name="updated_at")
    private LocalDateTime updatedAt;
    @PrePersist
    public void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }


    @ManyToMany(mappedBy = "film_genre")
    private Set<Genre> genres = new HashSet<>();



}
