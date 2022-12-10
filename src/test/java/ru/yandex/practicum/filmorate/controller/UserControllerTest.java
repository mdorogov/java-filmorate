package ru.yandex.practicum.filmorate.controller;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import ru.yandex.practicum.filmorate.exception.InvalidInputException;
import ru.yandex.practicum.filmorate.exception.ObjectAlreadyExistException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.UserStorage.InMemoryUserStorage;

import java.security.InvalidAlgorithmParameterException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest extends UserController {

    public UserControllerTest(UserService userService) {
        super(userService);
    }

    @Test
    void shouldPutUser() {
        User user = new User(1, "morozka@ya.se", "morozka", "Mroz Czerwony",
                LocalDate.of(1991, Month.JANUARY, 10));
        Assertions.assertEquals(user, createUser(user));
    }

    @Test
    void shouldNotPutUserWithUsedId() {
        User user = new User(1, "morozka@ya.se", "morozka", "Mroz Czerwony",
                LocalDate.of(1991, Month.JANUARY, 10));
        User user2 = new User(1, "morozka@ya.se", "morozka", "Mroz Czerwony",
                LocalDate.of(1991, Month.JANUARY, 10));
        createUser(user);
        ObjectAlreadyExistException exp = assertThrows(ObjectAlreadyExistException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                createUser(user2);
            }
        });
        assertEquals("Пользователь с таким ID  уже создан", exp.getMessage());
    }

    @Test
    void shouldNotPutUserWhenEmailIsWrong() {
        User user = new User(1, "morozkaya.se", "morozka", "Mroz Czerwony",
                LocalDate.of(1991, Month.JANUARY, 10));
        InvalidInputException exp = assertThrows(InvalidInputException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                createUser(user);
            }
        });
        assertEquals("Не соблюдены условия значений для добавления пользователя", exp.getMessage());
    }

    @Test
    void shouldPostUserWithNoId() {
        User user = new User(null, "mroz@ya.pl", "kate", "katedozor",
                LocalDate.of(1991, Month.JANUARY, 10));
        User expUser = new User(1, "mroz@ya.pl", "kate", "katedozor",
                LocalDate.of(1991, Month.JANUARY, 10));
        Assertions.assertEquals(expUser, createUser(user));
    }

    @Test
    void shouldPostUserWithNoId6() {
        User user = new User(1, "mroz@ya.pl", "kate", "katedozor",
                LocalDate.of(1991, Month.JANUARY, 10));
        User expUser = new User(1, "mroz@ya.pl", "kate", "katedozor",
                LocalDate.of(1991, Month.JANUARY, 10));
        Assertions.assertEquals(expUser, createUser(user));
    }

    @Test
    void shouldNotPutUserWhenEmailIsBlank() {
        User user = new User(1, " ", "morozka", "Mroz Czerwony",
                LocalDate.of(1991, Month.JANUARY, 10));
        InvalidInputException exp = assertThrows(InvalidInputException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                createUser(user);
            }
        });
        assertEquals("Не соблюдены условия значений для добавления пользователя", exp.getMessage());
    }

    @Test
    void shouldNotPutUserWhenDateIsNotPast() {
        User user = new User(1, "morozka@ya.se", "morozka", "Mroz Czerwony",
                LocalDate.of(2050, Month.JANUARY, 10));
        InvalidInputException exp = assertThrows(InvalidInputException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                createUser(user);
            }
        });
        assertEquals("Не соблюдены условия значений для добавления пользователя", exp.getMessage());
    }

    @Test
    void shouldNotPutUserWhenNameIsEmpty() {
        User user = new User(1, "morozka@ya.se", "morozka", null,
                LocalDate.of(1991, Month.JANUARY, 10));
        User check = createUser(user);
        assertEquals("morozka", check.getName());
    }

    @Test
    void shouldGetAllUsers() {
        User user = new User(1, "morozka@ya.se", "morozka", "Mroz Czerwony",
                LocalDate.of(1991, Month.JANUARY, 10));
        User user2 = new User(2, "moroz14ka@ya.se", "mrozec", "Mroz Niebieski",
                LocalDate.of(1992, Month.JANUARY, 10));
        createUser(user);
        createUser(user2);
        ArrayList<User> expUsers = new ArrayList<>();
        expUsers.add(user);
        expUsers.add(user2);
        ArrayList<User> values
                = new ArrayList<>(getAllUsers());
        Assertions.assertEquals(2, values.size());
        Assertions.assertEquals(expUsers, values);
    }

    @Test
    void shouldPostAndUpdateUser() {
        User user = new User(1, "morozka@ya.se", "morozka", "Mroz Czerwony",
                LocalDate.of(1991, Month.JANUARY, 10));
        User user2 = new User(1, "morozka@ya.se", "newmorozka", "New",
                LocalDate.of(1991, Month.JANUARY, 10));
        createUser(user);
        User updatedUser = updateUser(user2);
        assertEquals("New", updatedUser.getName());
    }


}