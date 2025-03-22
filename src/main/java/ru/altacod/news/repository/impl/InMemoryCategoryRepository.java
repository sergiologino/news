package ru.altacod.news.repository.impl;

import org.springframework.stereotype.Repository;
import ru.altacod.news.exception.EntityNotFoundException;
import ru.altacod.news.model.Category;
import ru.altacod.news.repository.CategoryRepository;
import ru.altacod.news.repository.CommentRepository;
import ru.altacod.news.repository.NewsRepository;
import ru.altacod.news.utils.BeanUtils;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryCategoryRepository implements CategoryRepository {

    private final Map<Long, Category> repository = new ConcurrentHashMap<>();

    private CommentRepository commentRepository;

    private NewsRepository newsRepository;

    private final AtomicLong currentId = new AtomicLong(1);

    @Override
    public List<Category> findAll() {

        return new ArrayList<>(repository.values());
    }

    @Override
    public Optional<Category> findById(Long id) {

        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public Category save(Category category) {
        Long categoryId = currentId.getAndIncrement();
        category.setId(categoryId);
        repository.put(categoryId,category);
        return category;
    }

    @Override
    public Category update(Category category) {
        Long categoryId = category.getId();
        Category currentCategory = repository.get(categoryId);
        if(currentCategory==null){
            throw new EntityNotFoundException((MessageFormat.format("Категория по ID {0} не найдена", categoryId)));
        }
        BeanUtils.copyNonNullProperties(category,currentCategory);
        currentCategory.setId(categoryId);
        repository.put(categoryId,currentCategory);
        return currentCategory;
    }

    @Override
    public void deleteById(Long id) {
        Category category=repository.get(id);
       /* if(category==null){
            throw new EntityNotFoundException((MessageFormat.format("Категория по ID {0} не найдена", id)));
        }*/
        throw new EntityNotFoundException((MessageFormat.format("Категории удалять нельзя. (Категория с id {0} ) ", id)));

    }
    public void deleteByIdIn(List<Long> ids){
        ids.forEach(repository::remove);
    }
}
