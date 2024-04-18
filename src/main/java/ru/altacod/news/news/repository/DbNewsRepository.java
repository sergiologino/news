package ru.altacod.news.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.altacod.news.news.model.News;

public interface DbNewsRepository extends JpaRepository<News, Long> {

}
