package ru.altacod.news.news.repository;

import ru.altacod.news.news.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

        List<Comment> findAll();

        Optional<Comment> findById(Long id);

        Comment save(Comment comment);

        Long getUserId(Comment comment);

        Comment update(Comment comment);

        void deleteById(Long id);

        void deleteByIdIn(List<Long> ids);
}

