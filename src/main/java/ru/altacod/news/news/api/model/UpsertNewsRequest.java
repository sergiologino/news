package ru.altacod.news.news.api.model;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertNewsRequest {

    @NotBlank(message = "Пользователь не может быть пустой!")
    private Long userId;

    @NotBlank(message = "Новость не может быть пустой!")
    private String description;

    @NotBlank(message = "Новость должна относиться к какой-либо категории! ")
    private Long categoryId;

    @NotBlank(message = "У новости должен быть заголовок! ")
    private String name;
}
