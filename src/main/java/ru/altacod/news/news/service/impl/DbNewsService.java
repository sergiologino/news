package ru.altacod.news.news.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;
import ru.altacod.news.news.aspect.Loggable;
import ru.altacod.news.news.exception.EntityNotFoundException;
import ru.altacod.news.news.model.News;
import ru.altacod.news.news.model.NewsFilter;
import ru.altacod.news.news.repository.DbNewsRepository;
import ru.altacod.news.news.repository.NewsSpecification;
import ru.altacod.news.news.service.NewsService;
import ru.altacod.news.news.utils.BeanUtils;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DbNewsService implements NewsService {

    private final DbNewsRepository newsRepository;

    @Override
    @Loggable
    public List<News> filterBy(NewsFilter filter) {
        return newsRepository.findAll(NewsSpecification.withFilter(filter),
                PageRequest.of(
                        filter.getPageNumber(), filter.getPageSize()
                )).getContent();
    }

    @Override
    @Loggable
    public List<News> findAll() {
        return newsRepository.findAll();
    }

    @Override
    @Loggable
    public News findById(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                        "Клиент с ID {0} не найден ", id)));
    }

    @Override
    @Loggable
    public News save(News news) {
        return newsRepository.save(news);
    }

    @Override
    @Loggable
    public News update(News news) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        var pathVariables = (Map<String, String>)
                request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        News existingNews = findById(news.getId());
        BeanUtils.copyNonNullProperties(news, existingNews);
        return newsRepository.save(news);
    }

    @Override
    @Loggable
    public void deleteById(Long id) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        var pathVariables = (Map<String, String>)
                request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        newsRepository.deleteById(id);

    }
}
