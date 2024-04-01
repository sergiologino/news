package ru.altacod.news.news.api.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpsertCommentRequest {

    @NotBlank(message = "Код пользователя не может быть пустой!")
    private Long userId;

    @NotBlank(message = "Комментарий не может быть пустой!")
    private String content;

    @NotBlank(message = "Код новости не может быть пустой!")
    private Long newsId;
}
