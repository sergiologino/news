package ru.altacod.news.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.altacod.news.api.model.CommentListResponse;
import ru.altacod.news.api.model.CommentResponse;
import ru.altacod.news.api.model.UpsertCommentRequest;
import ru.altacod.news.mapper.CommentMapper;
import ru.altacod.news.model.Comment;
import ru.altacod.news.model.User;
import ru.altacod.news.security.annotations.CanDeleteComment;
import ru.altacod.news.security.annotations.CanUpdateComment;
import ru.altacod.news.service.CommentService;
import ru.altacod.news.service.UserService;

@RestController
@RequestMapping("api/v1/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;

    private final CommentMapper commentMapper;

    @GetMapping
    public ResponseEntity<CommentListResponse> findAll() {
        return ResponseEntity.ok(commentMapper.commentListResponce(commentService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                commentMapper.commentToResponse(commentService.findById(id))
        );
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MODERATOR')")
    @PostMapping
    public ResponseEntity<CommentResponse> create(@RequestBody UpsertCommentRequest request) {
        String username = getCurrentUsername();
        User user = userService.findAll().stream()
                .filter(u -> u.getName().equals(username))
                .findFirst()
                .orElseThrow();

        Comment comment = commentMapper.requestToComment(request);
        comment.setUserId(user.getId());

        Comment newComment = commentService.save(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentMapper.commentToResponse(newComment));
    }

    @CanUpdateComment
    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> update(@PathVariable("id") Long commentId,
                                                  @RequestBody UpsertCommentRequest request) {
        Comment updatedComment = commentService.update(commentMapper.requestToComment(commentId, request));
        return ResponseEntity.ok(commentMapper.commentToResponse(updatedComment));
    }

    @CanDeleteComment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
