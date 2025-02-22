package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.InvalidInputException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.InMemoryFilmStorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public List<Film> getTopLikedFilms(int count) {
        log.info("Вызван метод getTop10LikedFilms");

        Comparator<Film> comp = (film1, film2) -> film2.getLikes().size() - film1.getLikes().size();

        return filmStorage.getAllFilms().stream().sorted(comp).limit(count).collect(Collectors.toList());
    }


    public Film addLike(int filmId, int userId) {
        if (!filmStorage.getFilms().containsKey(filmId)) {
            throw new InvalidInputException("Фильм с данным Id Отсутствует");
        }
        filmStorage.getFilmById(filmId).getLikes().add(userId);
        //обновление фильма?
        return filmStorage.getFilmById(filmId);
    }


    public Film removeLike(int filmId, int userId) {
        if (!filmStorage.getFilms().containsKey(filmId)) {
            throw new InvalidInputException("Фильма с данным ID нет");
        }
        if (userId < 0 || !filmStorage.getFilmById(filmId).getLikes().contains(userId)) {
            throw new InvalidInputException("Данный Like отсутствует'");
        }

        filmStorage.getFilmById(filmId).getLikes().remove(userId);
        return filmStorage.getFilmById(filmId);
    }


    public Collection<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }


    public Film createFilm(Film film) {
        return filmStorage.createFilm(film);
    }


    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }


    public int getUpdateIdNumber() {
        return filmStorage.getUpdateIdNumber();
    }


    public Map<Integer, Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film getFilmById(int filmId) {
        if (filmStorage.getFilms().containsKey(filmId)) {
            return filmStorage.getFilms().get(filmId);
        } else {
            throw new InvalidInputException("Id с таким фильм отсутствует");
        }

    }


}
