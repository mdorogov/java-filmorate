package ru.yandex.practicum.filmorate.storage.user.UserStorage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.InvalidInputException;
import ru.yandex.practicum.filmorate.exception.ObjectAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private static final Map<Integer, User> users = new HashMap<>();
    int idCounter = 1;

    @Override
    public User createUser(User user) {
        log.info("вызван метод create класса InMemoryUserStorage");
        String email = user.getEmail();
        String login = user.getLogin();
        String name = user.getName();
        LocalDate birthday = user.getBirthday();
        if (users.containsKey(user.getId())) {
            throw new ObjectAlreadyExistException("Пользователь с таким ID  уже создан");
        }
        if (!email.isBlank() && email.contains("@") && !login.isBlank() &&
                !login.contains(" ") && birthday.isBefore(LocalDate.now())) {
            if (name == null || name.isBlank()) {
                user.setName(login);
            }
            user.setId(getUpdateIdNumber());
            users.put(user.getId(), user);
        } else {
            throw new ValidationException("Не соблюдены условия значений для добавления пользователя");
        }
        return user;
    }

    @Override
    public int getUpdateIdNumber() {

        return idCounter++;
    }

    @Override
    public User updateUser(User user) {
        log.info("вызван метод updateUser");
        if (users.containsKey(user.getId())) {
            String email = user.getEmail();
            String login = user.getLogin();
            LocalDate birthday = user.getBirthday();
            if (!email.isBlank() && email.contains("@") && !login.isBlank() &&
                    !login.contains(" ") && birthday.isBefore(LocalDate.now())) {
                if (user.getName().isBlank()) {
                    user.setName(login);
                }
                if (users.containsKey(user.getId())) {
                    users.remove(user.getId());
                    users.put(user.getId(), user);
                } else {
                    throw new ValidationException("Не соблюдены условия значений для добавления фильма");
                }

            } else {
                throw new ValidationException("Не соблюдены условия значений для добавления фильма");
            }
            return user;
        } else {
            throw new InvalidInputException("Такого пользователя нет");
        }

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
}
