package ru.altacod.news.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.altacod.news.api.model.CategoryListResponse;
import ru.altacod.news.api.model.CategoryResponse;
import ru.altacod.news.api.model.UpsertCategoryRequest;
import ru.altacod.news.mapper.CategoryMapper;
import ru.altacod.news.model.Category;
import ru.altacod.news.service.CategoryService;

@RestController
@RequestMapping("api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    private final CategoryMapper categoryMapper;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MODERATOR')")
    @GetMapping
    public ResponseEntity<CategoryListResponse> findAll() {
        return ResponseEntity.ok(categoryMapper.categoryListResponse(categoryService.findAll()));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MODERATOR')")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryMapper.categoryToResponse(categoryService.findById(id)));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @PostMapping
    public ResponseEntity<CategoryResponse> create(@RequestBody UpsertCategoryRequest request) {
        Category newCategory = categoryService.save(categoryMapper.requestToCategory(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryMapper.categoryToResponse(newCategory));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable("id") Long categoryId,
                                                   @RequestBody UpsertCategoryRequest request) {
        Category updatedCategory = categoryService.update(categoryMapper.requestToCategory(categoryId, request));
        return ResponseEntity.ok(categoryMapper.categoryToResponse(updatedCategory));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
