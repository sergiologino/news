package ru.altacod.news.news.api.model;

import lombok.Data;

import java.util.List;


@Data
public class NewsForListResponse {

    private Long id;

    private String name;

    private String description;

    private Long userId;

    private Long categoryId;

    private Integer CommentQuantity;

    public void setNews(List<NewsForListResponse> collect) {

    }
}