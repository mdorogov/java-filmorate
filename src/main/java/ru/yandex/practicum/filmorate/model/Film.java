package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class Film {

    private int id;
    @NotBlank
    private String name;
    @NotNull
    private String description;
    private LocalDate releaseDate;
    private int duration;
    private List<String> genre = new ArrayList<>();
    private String rating;
    private Set<Integer> likes = new HashSet<>();
}
