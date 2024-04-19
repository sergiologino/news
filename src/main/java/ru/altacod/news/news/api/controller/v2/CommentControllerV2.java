package ru.altacod.news.news.api.controller.v2;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.altacod.news.news.api.model.CommentListResponse;
import ru.altacod.news.news.api.model.CommentResponse;
import ru.altacod.news.news.api.model.UpsertCommentRequest;
import ru.altacod.news.news.mapper.v2.CommentMapperV2;
import ru.altacod.news.news.model.Comment;
import ru.altacod.news.news.service.CommentService;

@RestController
@RequestMapping("/api/v2/comment")
@RequiredArgsConstructor
public class CommentControllerV2 {

    private final CommentService dbCommentService;

    private final CommentMapperV2 commentMapper;

    @GetMapping
    public ResponseEntity<CommentListResponse> findAll() {
        return ResponseEntity.ok(
                commentMapper.commentListToCommentListResponse(
                        dbCommentService.findAll()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                commentMapper.commentToResponse(dbCommentService.findById(id))
        );
    }

    @PostMapping
    public ResponseEntity<CommentResponse> create(@RequestBody @Valid UpsertCommentRequest request) {
        Comment newComment = dbCommentService.save(commentMapper.requestToComment(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentMapper.commentToResponse(newComment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> update(@PathVariable("id") Long commentId, @RequestBody @Valid UpsertCommentRequest request) {
        Comment updatedComment = dbCommentService.update(commentMapper.requestToComment(commentId, request));
        return ResponseEntity.ok(commentMapper.commentToResponse(updatedComment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        dbCommentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
