package ru.altacod.news.repository;

import ru.altacod.news.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    List<Category> findAll();

    Optional<Category> findById(Long id);

    Category save(Category category);

    Category update(Category category);

    void deleteById(Long id);
}
