package ru.altacod.news.service;

import ru.altacod.news.model.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> findAll();

    Comment findById(Long id);

    Comment save(Comment comment);

    Comment update(Comment comment);

    void deleteById(Long id);

    void deleteByIds(List<Long> ids);
}
