package ru.altacod.news.news.mapper.v2;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.altacod.news.news.api.model.CategoryListResponse;
import ru.altacod.news.news.api.model.CategoryResponse;
import ru.altacod.news.news.api.model.UpsertCategoryRequest;
import ru.altacod.news.news.model.Category;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapperV2 {

    Category requestToCategory(UpsertCategoryRequest request);

    @Mapping(source = "categoryId", target = "id")
    Category requestToCategory(Long categoryId, UpsertCategoryRequest request);

    CategoryResponse categoryToResponse(Category category);

    default CategoryListResponse categoryListToCategoryResponseList(List<Category> categories) {
        CategoryListResponse response = new CategoryListResponse();
        response.setCategories(categories.stream()
                .map(this::categoryToResponse).collect(Collectors.toList()));
        return response;
    }
}
