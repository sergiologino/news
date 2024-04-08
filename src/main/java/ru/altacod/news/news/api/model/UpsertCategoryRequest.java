package ru.altacod.news.news.api.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertCategoryRequest {


    @NotNull(message = "Название категории не может быть пустым")
    private String name;
}
