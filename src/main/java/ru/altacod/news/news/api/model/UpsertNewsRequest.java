package ru.altacod.news.news.api.model;

import lombok.Data;
import ru.altacod.news.news.model.Category;

@Data
public class UpsertNewsRequest {

    private Long userId;

    private String description;

    private Long categoryId;

    private String name;
}
