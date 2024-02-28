package ru.altacod.news.news.model;

import lombok.Data;

import java.time.Instant;

@Data
public class Comment {

    private Long id;

    private String content;

    private Long ownerId;

    private Instant createAt;

    private Instant updateAt;

    private News news;
}
