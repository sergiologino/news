package ru.altacod.news.news.mapper.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.altacod.news.news.api.model.CommentListResponse;
import ru.altacod.news.news.api.model.CommentResponce;
import ru.altacod.news.news.api.model.UpsertCommentRequest;
import ru.altacod.news.news.model.Comment;
import ru.altacod.news.news.service.NewsService;
import ru.altacod.news.news.service.impl.NewsServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final NewsMapper newsMapper;
    private final NewsService newsServiceImpl;

    public Comment requestToComment(UpsertCommentRequest request){
        Comment comment = new Comment();
        comment.setNews(request.getNews());
        comment.setContent(request.getContent());
        comment.setUserId(request.getUserId());

        return comment;
    }
    public Comment requestToComment(Long commentId, UpsertCommentRequest request){

        Comment comment = requestToComment(request);
        comment.setId(commentId);
        return comment;
    }

    public CommentResponce commentToResponce(Comment comment){
        CommentResponce commentResponce = new CommentResponce();
        commentResponce.setId(comment.getId());
        commentResponce.setContent(comment.getContent());
        commentResponce.setUserId(comment.getUserId());
        return commentResponce;
    }

    public List<CommentResponce> commentListToResponceList(List<Comment> comments){
        return comments.stream().map(this::commentToResponce).collect(Collectors.toList());
    }

    public CommentListResponse commentListResponce(List<Comment> comments){
        CommentListResponse response = new CommentListResponse();
        response.setComments(commentListToResponceList(comments));
        return response;
    }
}
