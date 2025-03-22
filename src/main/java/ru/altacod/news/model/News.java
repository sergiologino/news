package ru.altacod.news.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class News {

    private Long id;

    private String name;

    private String description;

    private Long userId;

    private Long categoryId;

    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment){
        comments.add(comment);
    }

    public void removeComment(Long commentId){
        comments = comments.stream().filter(c->!c.getId().equals(commentId)).collect(Collectors.toList());
    }
}
