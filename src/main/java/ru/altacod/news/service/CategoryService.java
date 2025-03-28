package ru.altacod.news.service;

import ru.altacod.news.model.Category;


import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    Category findById(Long id);

    Category save(Category category);

    Category update(Category category);

    void deleteById(Long id);

//    void deleteByIds(List<Long> ids);
}
