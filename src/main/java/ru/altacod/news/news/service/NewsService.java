package ru.altacod.news.news.service;

import org.springframework.boot.autoconfigure.pulsar.PulsarProperties;
import ru.altacod.news.news.model.News;

import java.util.List;

public interface NewsService {

    List<News> findAll();

    News findById(Long id);

    News save(News news);

    News update(News news);

    void deleteById(Long id);
}
