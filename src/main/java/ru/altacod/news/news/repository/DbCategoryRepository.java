package ru.altacod.news.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.altacod.news.news.model.Category;

public interface DbCategoryRepository extends JpaRepository<Category, Long> {
}
