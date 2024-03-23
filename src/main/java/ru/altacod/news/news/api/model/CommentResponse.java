package ru.altacod.news.news.api.model;

import lombok.Data;

@Data
public class CommentResponse {
    private Long id;

    private String content;

    private Long userId;

}
