package ru.altacod.news.news.model;

import lombok.Data;

import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class News {

    private Long id;

    private String name;

    private String description;

    private Long ownerId;

    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment){
        comments.add(comment);
    }

    public void removeComment(Long commentId){
        comments = comments.stream().filter(c->!c.getId().equals(commentId)).collect(Collectors.toList());
    }
}
