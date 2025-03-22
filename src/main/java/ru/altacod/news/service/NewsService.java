package ru.altacod.news.service;


import ru.altacod.news.model.News;

import java.util.List;

public interface NewsService {

    List<News> findAll();

    News findById(Long id);

    News save(News news);

    News update(News news);

    void deleteById(Long id);
}
