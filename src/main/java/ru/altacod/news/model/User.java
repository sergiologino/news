package ru.altacod.news.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class User {

    private Long id;

    private String name;

    private String password;

    private List<String> roles = new ArrayList<>();

}
