package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import ru.yandex.practicum.filmorate.exception.InvalidInputException;
import ru.yandex.practicum.filmorate.exception.ObjectAlreadyExistException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest extends FilmController {
    @Test
    void shouldPutFilm() {
        Film film = new Film(1, "Ветер", "Проверочное",
                LocalDate.of(2020, Month.JANUARY, 20), 60);
        Film filmBlank = new Film(2, "Ветер", "Проверочное",
                LocalDate.of(2020, Month.JANUARY, 20), 60);

        Assertions.assertEquals(film, createFilm(film));
    }

    @Test
    void shouldNotPutFilmWithUsedId() {
        Film film = new Film(1, "Ветер", "Проверочное",
                LocalDate.of(2020, Month.JANUARY, 20), 60);
        createFilm(film);

        ObjectAlreadyExistException exp = Assertions.assertThrows(ObjectAlreadyExistException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                createFilm(film);
            }
        });
        Assertions.assertEquals("Фильм с таким ID уже создан", exp.getMessage());

    }

    @Test
    void shouldNotPutFilmWhenDescriptionIsOver200() {
        Film film = new Film(3, "Ветер", "Проверочное",
                LocalDate.of(2020, Month.JANUARY, 20), 60);

        InvalidInputException exp = Assertions.assertThrows(InvalidInputException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                film.setDescription("org.springframework.boot.test.context.SpringBootTestContextBootstrapper. " +
                        "org.springframework.boot.test.context.SpringBootTestContextBootstrapper" +
                        "org.springframework.boot.test.context.SpringBootTestContextBootstrapper");
                createFilm(film);
            }
        });
        Assertions.assertEquals("Не соблюдены условия значений для добавления фильма", exp.getMessage());
    }

    @Test
    void shouldNotPutFilmWhenDateIsBeforeTheDay() {
        Film film = new Film(3, "Ветер", "Проверочное",
                LocalDate.of(1880, Month.JANUARY, 20), 60);

        InvalidInputException exp = Assertions.assertThrows(InvalidInputException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                createFilm(film);
            }
        });
        Assertions.assertEquals("Не соблюдены условия значений для добавления фильма", exp.getMessage());
    }

    @Test
    void shouldNotPutFilmWhenDurationIsZero() {
        Film film = new Film(3, "Ветер", "Проверочное",
                LocalDate.of(2024, Month.JANUARY, 20), 0);

        InvalidInputException exp = Assertions.assertThrows(InvalidInputException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                createFilm(film);
            }
        });
        Assertions.assertEquals("Не соблюдены условия значений для добавления фильма", exp.getMessage());
    }

    @Test
    void shouldNotPutFilmWithEmptyVar() {
        Film film = new Film(1, "", "Проверочное",
                LocalDate.of(2020, Month.JANUARY, 20), 60);

        InvalidInputException exp = Assertions.assertThrows(InvalidInputException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                createFilm(film);
            }
        });
        Assertions.assertEquals("Не соблюдены условия значений для добавления фильма", exp.getMessage());
    }

    @Test
    void shouldGetAllFilms() {
        Film film = new Film(1, "Ветер", "Проверочное",
                LocalDate.of(2020, Month.JANUARY, 20), 60);
        Film film2 = new Film(2, "Ветер2", "Проверочное2",
                LocalDate.of(2021, Month.JANUARY, 22), 62);
        createFilm(film);
        createFilm(film2);
        ArrayList<Film> expFilms = new ArrayList<>();
        expFilms.add(film);
        expFilms.add(film2);
        ArrayList<Film> values
                = new ArrayList<>(getAllFilms());
        Assertions.assertEquals(2, values.size());
        Assertions.assertEquals(expFilms, values);
    }

    @Test
    void shouldPostAndUpdateFilm() {
        Film film = new Film(1, "Ветер", "Проверочное",
                LocalDate.of(2020, Month.JANUARY, 20), 60);
        Film film2 = new Film(1, "Полнолуние", "Проверочное2",
                LocalDate.of(2021, Month.JANUARY, 22), 62);
        createFilm(film);
        Film updatedFilm = updateFilm(film2);
        assertEquals(film2, updatedFilm);
    }

}