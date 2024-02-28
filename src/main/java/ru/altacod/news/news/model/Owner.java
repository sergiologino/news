package ru.altacod.news.news.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Owner {

    private Long id;

    private String name;

    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment){
        comments.add(comment);
    }
}
