package ru.altacod.news.news.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.altacod.news.news.exception.EntityNotFoundException;
import ru.altacod.news.news.model.News;
import ru.altacod.news.news.repository.DbNewsRepository;
import ru.altacod.news.news.service.NewsService;
import ru.altacod.news.news.utils.BeanUtils;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DbNewsService implements NewsService {

    private final DbNewsRepository newsRepository;

    @Override
    public List<News> findAll() {
        return newsRepository.findAll();
    }

    @Override
    public News findById(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                        "Клиент с ID {0} не найден ", id)));
    }

    @Override
    public News save(News news) {
        return newsRepository.save(news);
    }

    @Override
    public News update(News news) {
        News existingNews = findById(news.getId());
        BeanUtils.copyNonNullProperties(news, existingNews);
        return newsRepository.save(news);
    }

    @Override
    public void deleteById(Long id) {
        newsRepository.findById(id);

    }
}
