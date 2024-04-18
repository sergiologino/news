package ru.altacod.news.news.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.altacod.news.news.exception.EntityNotFoundException;
import ru.altacod.news.news.model.Comment;
import ru.altacod.news.news.model.News;
import ru.altacod.news.news.repository.DbCommentRepository;
import ru.altacod.news.news.service.CommentService;
import ru.altacod.news.news.service.NewsService;
import ru.altacod.news.news.utils.BeanUtils;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DbCommentService implements CommentService {

    private final DbCommentRepository commentRepository;

    private final NewsService dbNewsService;

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                        "Комментарий с ID {0} не найден", id
                )));
    }

    @Override
    public Comment save(Comment comment) {
        News news = dbNewsService.findById(comment.getNews().getId());
        comment.setNews(news);
        return commentRepository.save(comment);

    }

    @Override
    public Comment update(Comment comment) {
        checkForUpdate(comment.getId(), comment.getUserId());
        News news = dbNewsService.findById(comment.getNews().getId());
        Comment existingComment = findById(comment.getId());
        BeanUtils.copyNonNullProperties(comment, existingComment);
        existingComment.setNews(news);
        return commentRepository.save(existingComment);
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);

    }

    @Override
    public void deleteByIds(List<Long> ids) {
        commentRepository.deleteAllById(ids);

    }
}
