package ru.yandex.practicum.filmorate.storage.user.UserStorage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.InvalidInputException;
import ru.yandex.practicum.filmorate.exception.ObjectAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private static final Map<Integer, User> users = new HashMap<>();
    int idCounter = 1;

    @Override
    public User createUser(User user) {
        log.info("вызван метод create класса InMemoryUserStorage");

        if (users.containsKey(user.getId())) {
            throw new ObjectAlreadyExistException("Пользователь с таким ID  уже создан");
        }
        if (!validation(user)) {
            throw new ValidationException("Не соблюдены условия значений для добавления пользователя");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(getUpdateIdNumber());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public int getUpdateIdNumber() {

        return idCounter++;
    }

    @Override
    public User updateUser(User user) {
        log.info("вызван метод updateUser");
        if (!users.containsKey(user.getId())) {
            throw new InvalidInputException("Нет такого пользователя");
        }
        if (!validation(user)) {
            throw new ValidationException("Не соблюдены условия значений для добавления фильма");
        }
                if (user.getName().isBlank()) {
                    user.setName(user.getLogin());
                }
                if (users.containsKey(user.getId())) {
                    users.remove(user.getId());
                    users.put(user.getId(), user);
                }
            return user;
    }

    @Override
    public Collection<User> getAllUsers() {
        log.info("вызван метод getAllUsers");
        if (users.isEmpty()) {
            throw new InvalidInputException("Список пользователей пуст");
        } else {
            return users.values();
        }
    }

    @Override
    public Map<Integer, User> getUsers() {

        return users;
    }

    @Override
    public User getUserById(int id) {
        return getUsers().get(id);
    }

    @Override
public boolean validation(User user) {
        String email = user.getEmail();
        String login = user.getLogin();
        LocalDate birthday = user.getBirthday();
        return !email.isBlank() && email.contains("@") && !login.isBlank() &&
                !login.contains(" ") && birthday.isBefore(LocalDate.now());
}
}
