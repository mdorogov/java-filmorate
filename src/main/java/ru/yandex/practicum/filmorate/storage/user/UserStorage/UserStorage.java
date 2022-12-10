package ru.yandex.practicum.filmorate.storage.user.UserStorage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Map;

public interface UserStorage {
    User createUser(User user);

    int getUpdateIdNumber();

    User updateUser(User user);

    Collection<User> getAllUsers();

    Map<Integer, User> getUsers();

    User getUserById(int id);

}
