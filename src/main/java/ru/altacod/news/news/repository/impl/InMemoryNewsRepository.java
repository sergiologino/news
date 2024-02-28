package ru.altacod.news.news.repository.impl;

import org.springframework.stereotype.Repository;
import ru.altacod.news.news.exception.EntityNotFoundException;
import ru.altacod.news.news.model.Comment;
import ru.altacod.news.news.model.News;
import ru.altacod.news.news.repository.CommentRepository;
import ru.altacod.news.news.repository.NewsRepository;
import ru.altacod.news.news.utils.BeanUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class InMemoryNewsRepository implements NewsRepository {

    private CommentRepository commentRepository;

    private final Map<Long,News> repository = new ConcurrentHashMap<>();

    private final AtomicLong currentId =new AtomicLong(1);
    @Override
    public List<News> findAll() {

        return new ArrayList<>(repository.values());
    }

    @Override
    public Optional<News> findById(Long id) {

        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public News save(News news) {
        Long newsId=currentId.getAndIncrement();
        news.setId(newsId);
        repository.put(newsId,news);
        return news;
    }

    @Override
    public News update(News news) {
        Long newsId=news.getId();
        News currentNews =repository.get(newsId);
        if (currentNews==null){
            throw new EntityNotFoundException(MessageFormat.format("Новость по ID {0} не найдена", newsId));
        }
        BeanUtils.copyNonNullProperties(news, currentNews);
        currentNews.setId(newsId);
        repository.put(newsId, currentNews);

        return currentNews;
    }

    @Override
    public void deleteById(Long id) {
        News news = repository.get(id);
        if (news==null){
            throw new EntityNotFoundException(MessageFormat.format("Новость по ID {0} не найдена", id));
        }

        commentRepository.deleteByIdIn(news.getComments().stream().map(Comment::getId).collect(Collectors.toList()));
        repository.remove(id);
    }

    public void setNewsRepository(CommentRepository commentRepository){
        this.commentRepository=commentRepository;
    }
}
