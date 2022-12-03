package ru.yandex.practicum.filmorate.exception;

public class ObjectAlreadyExistException extends RuntimeException {
    public ObjectAlreadyExistException(final String message) {
        super(message);
    }
}
