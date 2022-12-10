package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage.UserStorage;

import java.sql.Array;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest extends UserService {

    public UserServiceTest(UserStorage userStorage) {
        super(userStorage);
    }

    @Test
    void addFriend() {

    }

    @Test
    void removeFriend() {
    }

    @Test
    void getAllFriendsOfUserById() {
    }

    @Test
    void getCommonFriends() {
    }
}