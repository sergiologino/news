package ru.altacod.news.news.api.model;

import lombok.Data;

import java.time.Instant;

@Data
public class CommentResponse {
    private Long id;

    private String content;

    private Long userId;

    private Instant createAt;

    private Instant updateAt;

}
