package ru.altacod.news.news.mapper.v2;

import org.springframework.beans.factory.annotation.Autowired;
import ru.altacod.news.news.api.model.UpsertCommentRequest;
import ru.altacod.news.news.model.Comment;
import ru.altacod.news.news.service.NewsService;

public abstract class CommentMapperDelegate implements CommentMapperV2 {

    @Autowired
    private NewsService dbNewsService;

    @Override
    public Comment requestToComment(UpsertCommentRequest request) {
        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setUserId(request.getUserId());
        comment.setNews(dbNewsService.findById(request.getNewsId()));

        return comment;
    }

}
