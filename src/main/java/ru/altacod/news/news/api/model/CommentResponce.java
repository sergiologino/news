package ru.altacod.news.news.api.model;

import lombok.Data;

@Data
public class CommentResponce {
    private Long id;

    private String content;

    private Long userId;

}
