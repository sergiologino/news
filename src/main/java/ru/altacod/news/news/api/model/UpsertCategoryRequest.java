package ru.altacod.news.news.api.model;

import lombok.Data;

@Data
public class UpsertCategoryRequest {
    private Long id;
    private String name;
}
