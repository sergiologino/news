package ru.altacod.news.news.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Data
@Getter
@Setter
public class Comment {

    private Long id;

    private String content;

    private Long userId;

    private Instant createAt;

    private Instant updateAt;

    private Long newsId;

}

