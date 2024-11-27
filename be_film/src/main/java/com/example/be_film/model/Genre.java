package com.example.be_film.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="genre")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="genre_name")
    private String genreName;

    @ManyToMany
    @JoinTable(
            name = "genre_film",
            joinColumns = @JoinColumn(name = "genreid"),
            inverseJoinColumns = @JoinColumn(name = "filmid"))
    private Set<Film> film_genre = new HashSet<>();

}
