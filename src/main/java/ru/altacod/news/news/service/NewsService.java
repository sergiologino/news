package ru.altacod.news.news.service;

import ru.altacod.news.news.model.News;
import ru.altacod.news.news.model.NewsFilter;

import java.util.List;

public interface NewsService {

    List<News> filterBy(NewsFilter filter);

    List<News> findAll();

    News findById(Long id);

    News save(News news);

    News update(News news);

    void deleteById(Long id);
}
