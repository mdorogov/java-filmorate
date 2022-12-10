package ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.InvalidInputException;
import ru.yandex.practicum.filmorate.exception.ObjectAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    int idCounter = 1;
    private final LocalDate birthOfCinema = LocalDate.of(1895, Month.DECEMBER, 28);


    @Override
    public Collection<Film> getAllFilms() {
        log.info("Вызван метод getAllFilms класса InMemoryFilmStorage");
        return films.values();
    }

    @Override
    public Film createFilm(Film film) {
        log.info("вызван метод createFilm");
        Integer id = film.getId();
        if (films.containsKey(id)) {
            throw new ObjectAlreadyExistException("Фильм с таким ID уже создан");
        }
        if (!film.getName().isBlank() && (film.getDescription().length() <= 200) &&
                (film.getReleaseDate().isAfter(birthOfCinema)) && (film.getDuration() > 0)) {
            film.setId(getUpdateIdNumber());
            films.put(film.getId(), film);
        } else {
            throw new ValidationException("Не соблюдены условия значений для добавления фильма");
        }
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        log.info("Вызван метод updateFilm");
        if (!films.containsKey(film.getId())) {
            throw new InvalidInputException("Фильм с таким ID отсутствует");
        }
        if (!film.getName().isBlank() && (film.getDescription().length() <= 200) &&
                (film.getReleaseDate().isAfter(birthOfCinema)) && (film.getDuration() > 0)) {
            if (films.containsKey(film.getId())) {
                films.remove(film.getId());
            }
            films.put(film.getId(), film);
        } else {
            throw new ValidationException("Не соблюдены условия значений для добавления фильма");
        }
        return film;
    }

    @Override
    public int getUpdateIdNumber() {
        log.info("метод обновления ID вызван");
        return idCounter++;
    }

    @Override
    public Map<Integer, Film> getFilms() {

        log.info("Вызван метод getFilms");
        return films;
    }

    @Override
    public Film getFilmById(int id) {
        return films.get(id);
    }


}
