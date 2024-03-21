package ru.altacod.news.news.api.model;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String name;
    private String password;
}
