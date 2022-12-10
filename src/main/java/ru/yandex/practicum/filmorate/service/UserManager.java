package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Map;

public interface UserManager {
    User addFriend(Integer userId, Integer friendId);
    User removeFriend(Integer userId, Integer friendId);
    Collection<User> getAllFriendsOfUserById(Integer id);
}
