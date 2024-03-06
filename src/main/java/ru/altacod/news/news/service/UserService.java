package ru.altacod.news.news.service;

import ru.altacod.news.news.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById (Long id);

    User save(User user);

    User update(User user);
}
