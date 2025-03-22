package ru.altacod.news.service;

import ru.altacod.news.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById (Long id);

    User save(User user);

    User update(User user);

    User delete(User user);
}
