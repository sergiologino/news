package ru.altacod.news.news.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.altacod.news.news.model.News;

public interface DbNewsRepository extends JpaRepository<News, Long>, JpaSpecificationExecutor<News> {
    Page<News> findAllNewsByCategoryId(Long categoryId, Long userId, Pageable pageable);

}
