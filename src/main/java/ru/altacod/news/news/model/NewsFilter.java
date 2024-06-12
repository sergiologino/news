package ru.altacod.news.news.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewsFilter {

    private Integer pageSize;

    private Integer pageNumber;

    private Long categoryId;

    private Long userId;

}
