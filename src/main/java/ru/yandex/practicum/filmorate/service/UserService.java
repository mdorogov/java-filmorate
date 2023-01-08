package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.InvalidInputException;
import ru.yandex.practicum.filmorate.exception.ObjectAlreadyExistException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage.UserStorage;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }


    public List<User> addFriend(int userId, int friendId) {

        log.info("вызван метод addFriend userService");
        if (!userStorage.getUsers().containsKey(userId) || !getUsers().containsKey(friendId)) {
            throw new InvalidInputException("Пользователей не существует");
        }
        if (userStorage.getUserById(userId).getFriends().contains(friendId)) {
            throw new ObjectAlreadyExistException("Пользователи уже друзья");
        }
        userStorage.getUserById(userId).getFriends().add(friendId);
        userStorage.getUserById(friendId).getFriends().add(userId);

        return Arrays.asList(userStorage.getUserById(userId), userStorage.getUserById(friendId));


    }

    public User removeFriend(int userId, int friendId) {
        userStorage.getUsers().get(userId).getFriends().remove(friendId);
        userStorage.getUsers().get(friendId).getFriends().remove(userId);

        return userStorage.getUsers().get(userId);
    }

    public List<User> getAllFriendsOfUserById(int id) {
        if (userStorage.getUsers().containsKey(id)) {
            return userStorage.getUsers().get(id).getFriends().stream().map(userStorage::getUserById).collect(Collectors.toList());
        } else {
            throw new InvalidInputException("Пользователь не найден");
        }

    }

    public List<User> getCommonFriends(int userId, int friendId) {

        if (getUsers().containsKey(userId) && getUsers().containsKey(friendId)) {
            User user = userStorage.getUserById(userId);
            User friendUser = userStorage.getUserById(friendId);
            return user.getFriends().stream()
                    .filter(id -> friendUser.getFriends()
                            .contains(id))
                    .map(userStorage::getUserById)
                    .collect(Collectors.toList());

        } else {
            throw new InvalidInputException("Таких пользователей нет.");
        }
    }

    public User createUser(User user) {
        return userStorage.createUser(user);
    }


    public int getUpdateIdNumber() {
        return userStorage.getUpdateIdNumber();
    }


    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }


    public Collection<User> getAllUsers() {
        return userStorage.getAllUsers();
    }


    public Map<Integer, User> getUsers() {
        return userStorage.getUsers();
    }


    public User getUserById(int id) {
        if (!userStorage.getUsers().containsKey(id)) {
            throw new InvalidInputException("Пользователь не найден");
        }
        return userStorage.getUserById(id);
    }


}
