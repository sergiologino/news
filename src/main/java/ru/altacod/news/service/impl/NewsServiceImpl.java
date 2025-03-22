package ru.altacod.news.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.altacod.news.exception.EntityNotFoundException;
import ru.altacod.news.model.News;
import ru.altacod.news.repository.NewsRepository;
import ru.altacod.news.service.NewsService;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    @Override
    public List<News> findAll() {
        return newsRepository.findAll();
    }

    @Override
    public News findById(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException(MessageFormat.format("Клиент с ID {0] не найден!",id)));
    }

    @Override
    public News save(News news) {
        return newsRepository.save(news);
    }

    @Override
    public News update(News news) {
        return newsRepository.update(news);
    }

    @Override
    public void deleteById(Long id) {
        newsRepository.deleteById(id);

    }
}
