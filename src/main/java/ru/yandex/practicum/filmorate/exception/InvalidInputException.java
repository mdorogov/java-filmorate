package ru.yandex.practicum.filmorate.exception;

public class InvalidInputException extends RuntimeException{
    public InvalidInputException(final String message) {
        super(message);
    }
}
