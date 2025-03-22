package ru.altacod.news.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.altacod.news.api.model.CommentListResponse;
import ru.altacod.news.api.model.CommentResponse;
import ru.altacod.news.api.model.UpsertCommentRequest;
import ru.altacod.news.model.Comment;
import ru.altacod.news.service.NewsService;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final NewsMapper newsMapper;
    private final NewsService newsServiceImpl;

    public Comment requestToComment(UpsertCommentRequest request) {
        Comment comment = new Comment();
        comment.setNewsId(request.getNewsId());
        comment.setContent(request.getContent());


        return comment;
    }

    public Comment requestToComment(Long commentId, UpsertCommentRequest request) {

        Comment comment = requestToComment(request);
        comment.setId(commentId);
        return comment;
    }

    public CommentResponse commentToResponse(Comment comment) {
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setId(comment.getId());
        commentResponse.setContent(comment.getContent());
        return commentResponse;
    }

    public List<CommentResponse> commentListToResponseList(List<Comment> comments) {
        return comments.stream().map(this::commentToResponse).collect(Collectors.toList());
    }

    public CommentListResponse commentListResponce(List<Comment> comments) {
        CommentListResponse response = new CommentListResponse();
        response.setComments(commentListToResponseList(comments));
        return response;
    }
}
