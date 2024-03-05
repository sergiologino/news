package ru.altacod.news.news.service;

import ru.altacod.news.news.model.Category;
import ru.altacod.news.news.model.Comment;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    Category findById(Long id);

    Category save(Category category);

    Category update(Category category);

    void deleteById(Long id);

//    void deleteByIds(List<Long> ids);
}
