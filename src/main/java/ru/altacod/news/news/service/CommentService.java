package ru.altacod.news.news.service;

import ru.altacod.news.news.exception.UpdateStateException;
import ru.altacod.news.news.model.Comment;

import java.util.List;
import java.util.Objects;

public interface CommentService {

    List<Comment> findAll();

    Comment findById(Long id);

    Comment save(Comment comment);

    Comment update(Comment comment);

    void deleteById(Long id);

    void deleteByIds(List<Long> ids);

    default void checkForUpdate(Long commentId, Long userId) {
        Comment currentComment = findById(commentId);
        Long currentUserId = currentComment.getUserId();

        if (!Objects.equals(currentUserId, userId)) {
            throw new UpdateStateException("Изменять  и удалять комментарий может только автор комментария!");
        }

    }
}
