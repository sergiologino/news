package ru.altacod.news.repository;

import ru.altacod.news.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();

    Optional<User> findById(Long id);

    User save(User user);

    User update(User user);

    User delete(User user);

    void deleteById(Long id);

//    void deleteById(Long id);
}
