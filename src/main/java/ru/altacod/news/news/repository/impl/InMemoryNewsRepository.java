package ru.altacod.news.news.repository.impl;

import org.springframework.stereotype.Repository;
import ru.altacod.news.news.model.News;
import ru.altacod.news.news.repository.NewsRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryNewsRepository implements NewsRepository {

    private NewsRepository newsRepository;
    @Override
    public List<News> findAll() {
        return null;
    }

    @Override
    public Optional<News> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public News save(News news) {
        return null;
    }

    @Override
    public News update(News news) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    public void setNewsRepository(NewsRepository newsRepository){
    this.newsRepository=newsRepository;
    }
}
