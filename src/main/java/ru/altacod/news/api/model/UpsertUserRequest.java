package ru.altacod.news.api.model;

import lombok.Data;

import java.util.List;

@Data
public class UpsertUserRequest {
    private String name;

    private String password;

    private List<String> roles;
}

