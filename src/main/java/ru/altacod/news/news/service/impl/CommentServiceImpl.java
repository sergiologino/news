package ru.altacod.news.news.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.altacod.news.news.exception.EntityNotFoundException;
import ru.altacod.news.news.model.Comment;
import ru.altacod.news.news.repository.CommentRepository;
import ru.altacod.news.news.service.CommentService;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException(MessageFormat.format("Комментарий с ID {id} не найден",id)));
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment update(Comment comment) {
        checkForUpdate(comment.getId(), comment.getUserId());
        return commentRepository.update(comment);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Comment> currentComment=commentRepository.findById(id);
        Long currentUserId = currentComment.get().getUserId();
        checkForUpdate(id, currentUserId);
        commentRepository.deleteById(id);

    }

    @Override
    public void deleteByIds(List<Long> ids) {
        commentRepository.deleteByIdIn(ids);
    }

}
