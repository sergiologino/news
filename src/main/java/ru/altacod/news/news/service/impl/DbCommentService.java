package ru.altacod.news.news.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;
import ru.altacod.news.news.aspect.Loggable;
import ru.altacod.news.news.exception.EntityNotFoundException;
import ru.altacod.news.news.model.Comment;
import ru.altacod.news.news.model.News;
import ru.altacod.news.news.repository.DbCommentRepository;
import ru.altacod.news.news.service.CommentService;
import ru.altacod.news.news.service.NewsService;
import ru.altacod.news.news.utils.BeanUtils;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DbCommentService implements CommentService {

    private final DbCommentRepository commentRepository;

    private final NewsService dbNewsService;

    @Override
    @Loggable
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    @Loggable
    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                        "Комментарий с ID {0} не найден", id
                )));
    }

    @Override
    @Loggable
    public Comment save(Comment comment) {
        News news = dbNewsService.findById(comment.getNews().getId());
        comment.setNews(news);
        return commentRepository.save(comment);

    }

    @Override
    @Loggable
    public Comment update(Comment comment) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        var pathVariables = (Map<String, String>)
                request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        checkForUpdate(comment.getId(), comment.getUserId());
        News news = dbNewsService.findById(comment.getNews().getId());
        Comment existingComment = findById(comment.getId());
        BeanUtils.copyNonNullProperties(comment, existingComment);
        existingComment.setNews(news);
        return commentRepository.save(existingComment);
    }

    @Override
    @Loggable
    public void deleteById(Long id) {
        commentRepository.deleteById(id);

    }

    @Override
    @Loggable
    public void deleteByIds(List<Long> ids) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        var pathVariables = (Map<String, String>)
                request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        commentRepository.deleteAllById(ids);

    }
}
