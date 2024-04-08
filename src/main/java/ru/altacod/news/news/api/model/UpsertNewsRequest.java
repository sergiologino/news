package ru.altacod.news.news.api.model;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertNewsRequest {

 @NotNull(message = "Пользователь не может быть пустой!")
 private Long userId;

 @NotNull(message = "Новость должна относиться к какой-либо категории! ")
 private Long categoryId;

 @NotNull(message = "У новости должен быть заголовок! ")
 private String name;

 @NotNull(message = "Текст новости не может быть пустой!")
 private String description;


}
