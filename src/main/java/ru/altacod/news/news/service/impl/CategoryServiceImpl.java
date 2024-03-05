package ru.altacod.news.news.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.altacod.news.news.exception.EntityNotFoundException;
import ru.altacod.news.news.model.Category;
import ru.altacod.news.news.repository.CategoryRepository;
import ru.altacod.news.news.service.CategoryService;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException(MessageFormat.format("Категория с указанным id {0} не найдена!",id)));
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Category category) {
        return categoryRepository.update(category);
    }

    @Override
    public void deleteById(Long id) {

    }

//    @Override
//    public void deleteByIds(List<Long> ids) {
//        categoryRepository.deleteById();
//
//    }
}
