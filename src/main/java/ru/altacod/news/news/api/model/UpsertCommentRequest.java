package ru.altacod.news.news.api.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertCommentRequest {

    @NotNull(message = "Код пользователя не может быть пустой!")
    private Long userId;

    @NotNull(message = "Код новости не может быть пустой! Комментарий должен относиться к новости")
    private Long newsId;

    @NotNull(message = "Комментарий не может быть пустой!")
    private String content;


}
