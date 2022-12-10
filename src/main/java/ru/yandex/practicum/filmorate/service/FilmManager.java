package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmManager {
    Collection<Film> getTop10FilmsByLikesAmmount();
    Film addLike(Integer filmId, Integer userId);
    Film removeLike(Integer filmId, Integer userId);

}
