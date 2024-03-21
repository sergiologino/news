package ru.altacod.news.news.api.model;

import lombok.Data;
import ru.altacod.news.news.model.News;

@Data
public class UpsertCommentRequest {
    private Long userId;

    private String content;

    private News news;
}
