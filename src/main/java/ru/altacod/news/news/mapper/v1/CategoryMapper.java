package ru.altacod.news.news.mapper.v1;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.altacod.news.news.api.model.CategoryListResponse;
import ru.altacod.news.news.api.model.CategoryResponse;
import ru.altacod.news.news.api.model.UpsertCategoryRequest;
import ru.altacod.news.news.model.Category;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryMapper {

    public Category requestToCategory(UpsertCategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        return category;
    }

    public Category requestToCategory(Long categoryId, UpsertCategoryRequest request) {
        Category category = requestToCategory(request);
        category.setId(categoryId);
        return category;
    }

    public CategoryResponse categoryToResponse(Category category) {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());
        return categoryResponse;
    }

    public List<CategoryResponse> categoryListToResponseList(List<Category> categories) {
        return categories.stream().map(this::categoryToResponse).collect(Collectors.toList());
    }

    public CategoryListResponse categoryListResponse(List<Category> categories) {
        CategoryListResponse response = new CategoryListResponse();
        response.setCategories(categoryListToResponseList(categories));
        return response;
    }
}
