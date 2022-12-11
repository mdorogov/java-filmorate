package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.InvalidInputException;
import ru.yandex.practicum.filmorate.exception.ObjectAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.security.InvalidAlgorithmParameterException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j

@RestController
@RequestMapping("/films")
public class FilmController {
    @Autowired
    private final FilmService filmService;


    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public Collection<Film> getAllFilms() {
        log.info("Создан запрос GET");
        return filmService.getAllFilms();
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        log.info("Создан запрос POST ");
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("Создан запрос PUT");
        return filmService.updateFilm(film);
    }

    @GetMapping("/{filmId}")
    public Film getFilmById(@PathVariable int filmId) {
        log.info("Создан запрос GET");
        return filmService.getFilmById(filmId);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Создан запрос PUT");
        return filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film removeLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Создан запрос DELETE");
        return filmService.removeLike(id, userId);
    }


    @GetMapping("/popular")
    public List<Film> getTopFilms(@RequestParam(defaultValue = "10", required = false) int count) {
        log.info("Создан запрос GET");
        return filmService.getTopLikedFilms(count);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(ValidationException e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleInvalidInputException(InvalidInputException e) {
        return Map.of("error not found", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleObjectAlreadyExistException(ObjectAlreadyExistException e) {
        return Map.of("internal error", e.getMessage());
    }


}
