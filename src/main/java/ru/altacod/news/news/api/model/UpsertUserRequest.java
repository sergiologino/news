package ru.altacod.news.news.api.model;

import lombok.Data;

@Data
public class UpsertUserRequest {
    private String name;

    private String password;
}
