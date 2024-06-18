package ru.altacod.news.news.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewsFilter {

    private Integer pageSize;

    private Integer pageNumber;

    private Long categoryId;

    private Long userId;
}
