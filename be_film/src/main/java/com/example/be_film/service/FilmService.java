package com.example.be_film.service;

import com.example.be_film.dtos.FilmDTO;
import com.example.be_film.exceptions.DataNotFoundException;
import com.example.be_film.exceptions.GenreNotFoundException;
import com.example.be_film.model.Film;
import com.example.be_film.model.FilmGenre;
import com.example.be_film.model.Genre;
import com.example.be_film.model.keys.KeyGenreFilm;
import com.example.be_film.repositories.FilmGenreRepository;
import com.example.be_film.repositories.FilmRepository;
import com.example.be_film.repositories.GenreRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FilmService implements iFilmService {
    private final FilmRepository filmRepository;
    private final GenreRepository genreRepository;
    private final FilmGenreRepository filmGenreRepository;

    @Override
    public Film createFilm(FilmDTO filmDTO) {
        Film newFilm = Film.builder()
                .filmName(filmDTO.getFilmname())
                .imgFilm(filmDTO.getImgFilm())
                .imgBannerFilm(filmDTO.getImgBannerFilm())
                .urlFilm(filmDTO.getUrlFilm())
                .duration(filmDTO.getDuration())
                .status(filmDTO.getStatus())
                .caption(filmDTO.getCaption())
                .description(filmDTO.getDescription())
                .build();

        Film savedFilm = filmRepository.save(newFilm);

        // Tạo các bản ghi trong bảng genre_film
        Set<FilmGenre> filmGenres = new HashSet<>();
        for (Long genreId : filmDTO.getGenreIds()) {
            Genre genre = genreRepository.findById(genreId)
                    .orElseThrow(() -> new GenreNotFoundException("Genre not found with id: " + genreId));

            FilmGenre filmGenre = new FilmGenre(new KeyGenreFilm(savedFilm.getId(), genre.getId()));
            filmGenre.setFilms(savedFilm);
            filmGenre.setGenres(genre);

            filmGenres.add(filmGenre);
        }

        filmGenreRepository.saveAll(filmGenres);

        return savedFilm;
    }


    @Override
    public Film getFilmById(long id) throws DataNotFoundException {
        return filmRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Film not found with id: " + id));
    }

    @Override
    public Page<Film> getAllFilm(PageRequest pageRequest) {
        return filmRepository.findAll(pageRequest);
    }

    @Override
    public Film updateFilm(long id, FilmDTO filmDTO) throws Exception {
        // Lấy thông tin phim hiện tại
        Film existingFilm = getFilmById(id);

        // Cập nhật các trường thông tin cơ bản
        existingFilm.setFilmName(filmDTO.getFilmname());
        existingFilm.setImgFilm(filmDTO.getImgFilm());
        existingFilm.setUrlFilm(filmDTO.getUrlFilm());
        existingFilm.setDuration(filmDTO.getDuration());
        existingFilm.setCaption(filmDTO.getCaption());
        existingFilm.setStatus(filmDTO.getStatus());
        existingFilm.setDescription(filmDTO.getDescription());

        // Xóa các liên kết cũ trong bảng genre_film
        filmGenreRepository.deleteAllByFilms(existingFilm);

        // Tạo và lưu các liên kết mới trong bảng genre_film
        Set<FilmGenre> newFilmGenres = new HashSet<>();
        for (Long genreId : filmDTO.getGenreIds()) {
            Genre genre = genreRepository.findById(genreId)
                    .orElseThrow(() -> new GenreNotFoundException("Genre not found with id: " + genreId));

            FilmGenre filmGenre = new FilmGenre(new KeyGenreFilm(existingFilm.getId(), genre.getId()));
            filmGenre.setFilms(existingFilm);
            filmGenre.setGenres(genre);

            newFilmGenres.add(filmGenre);
        }
        filmGenreRepository.saveAll(newFilmGenres);

        // Trả về đối tượng `Film` đã được cập nhật
        return filmRepository.save(existingFilm);
    }


    @Override
    public void deleteFilm(long id) throws DataNotFoundException {
        Film existingFilm = filmRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Film not found with id: " + id));
        filmRepository.delete(existingFilm);
    }
    public List<Film> searchFilms(String keyword, int less) {
        // Thực hiện tìm kiếm bằng cách sử dụng keyword và giới hạn số lượng kết quả
        return filmRepository.findTopByKeyword(keyword, PageRequest.of(0, less));
    }


    @Override
    public boolean existByName(String nameFilm) {
        return filmRepository.existsByFilmName(nameFilm);
    }
}
