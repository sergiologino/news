package ru.altacod.news.news.api.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NewsResponse {
    private Long id;

    private String name;

    private String description;

    private Long userId;

    private Long categoryId;

    private List<CommentResponse> comments = new ArrayList<>();
}
