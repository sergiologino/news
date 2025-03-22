package ru.altacod.news.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.altacod.news.news.model.User;

public interface DbUserRepository extends JpaRepository<User, Long> {
}
