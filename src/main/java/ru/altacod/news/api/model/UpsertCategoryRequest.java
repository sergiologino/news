package ru.altacod.news.api.model;

import lombok.Data;

@Data
public class UpsertCategoryRequest {
    private Long id;
    private String name;
}
