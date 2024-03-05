package ru.altacod.news.news.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.altacod.news.news.exception.EntityNotFoundException;
import ru.altacod.news.news.model.Comment;
import ru.altacod.news.news.model.News;
import ru.altacod.news.news.repository.CommentRepository;
import ru.altacod.news.news.repository.NewsRepository;
import ru.altacod.news.news.utils.BeanUtils;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryCommentRepository implements CommentRepository {

    private NewsRepository newsRepository;

    private final Map<Long, Comment> repository = new ConcurrentHashMap<>();

    private final AtomicLong currentId = new AtomicLong(1);

    @Autowired
    public void setNewsRepository(NewsRepository newsRepository){
        this.newsRepository=newsRepository;
    }

    @Override
    public List<Comment> findAll() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public Comment save(Comment comment) {
        Long commentId = currentId.getAndIncrement();
        Long newsId = comment.getNews().getId();
        News news = newsRepository.findById(newsId)
                        .orElseThrow(()-> new EntityNotFoundException("Новость не найдена"));
        comment.setNews(news);
        comment.setId(commentId);
        Instant now = Instant.now();
        comment.setCreateAt(now);
        comment.setUpdateAt(now);

        repository.put(commentId,comment);

        news.addComment(comment);
        newsRepository.update(news);
        return comment;
    }

    @Override
    public Long getUserId(Comment comment) {
        return comment.getUserId();
    }

    @Override
    public Comment update(Comment comment) {

        Long commentId=comment.getId();
        Instant now = Instant.now();
        Comment currentComment=repository.get(commentId);

        if(currentComment==null){
            throw new EntityNotFoundException(MessageFormat.format("Комментарий по {0} не найден", commentId));
        }

        BeanUtils.copyNonNullProperties(comment, currentComment);

        currentComment.setUpdateAt(now);
        currentComment.setId(commentId);

        repository.put(commentId, currentComment);
        return currentComment;
    }

    @Override
    public void deleteById(Long id) {
        repository.remove(id);

    }

    @Override
    public void deleteByIdIn(List<Long> ids) {
        ids.forEach(repository::remove);

    }
}
