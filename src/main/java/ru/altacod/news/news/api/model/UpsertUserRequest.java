package ru.altacod.news.news.api.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertUserRequest {

    @NotNull(message = "Имя пользователя не может быть пустым!")
    private String name;

    @NotNull(message = "Пароль пользователя не может быть пустым!")
    private String password;
}
