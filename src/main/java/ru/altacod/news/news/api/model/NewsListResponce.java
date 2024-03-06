package ru.altacod.news.news.api.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NewsListResponce {
    private List<NewsResponce> news =new ArrayList<>();
}
