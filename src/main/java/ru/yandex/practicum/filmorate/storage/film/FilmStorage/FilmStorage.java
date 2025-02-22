package ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Map;

public interface FilmStorage {
    Collection<Film> getAllFilms();

    Film createFilm(Film film);

    Film updateFilm(Film film);

    int getUpdateIdNumber();

    Map<Integer, Film> getFilms();

    Film getFilmById(int id);

    boolean validation(Film film);
}
