package ru.altacod.news.news.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.altacod.news.news.exception.EntityNotFoundException;
import ru.altacod.news.news.model.News;
import ru.altacod.news.news.model.NewsFilter;
import ru.altacod.news.news.repository.DbNewsRepository;
import ru.altacod.news.news.repository.NewsSpecification;
import ru.altacod.news.news.service.NewsService;
import ru.altacod.news.news.utils.BeanUtils;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DbNewsService implements NewsService {

    private final DbNewsRepository newsRepository;

    @Override
    public List<News> filterBy(NewsFilter filter) {
        return newsRepository.findAll(NewsSpecification.withFilter(filter),
                PageRequest.of(
                        filter.getPageNumber(), filter.getPageSize()
                )).getContent();
    }

    @Override
    public List<News> findAll() {
        return newsRepository.findAll();
    }

    @Override
    public News findById(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                        "Новость с ID {0} не найдена ", id)));
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
