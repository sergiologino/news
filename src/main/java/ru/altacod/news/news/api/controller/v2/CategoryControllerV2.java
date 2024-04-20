package ru.altacod.news.news.api.controller.v2;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.altacod.news.news.api.model.CategoryListResponse;
import ru.altacod.news.news.api.model.CategoryResponse;
import ru.altacod.news.news.api.model.UpsertCategoryRequest;
import ru.altacod.news.news.mapper.v2.CategoryMapperV2;
import ru.altacod.news.news.model.Category;
import ru.altacod.news.news.service.CategoryService;

@RestController
@RequestMapping("/api/v2/category")
@RequiredArgsConstructor
public class CategoryControllerV2 {

    private final CategoryService dbCategoryService;

    private final CategoryMapperV2 categoryMapper;

    @GetMapping
    public ResponseEntity<CategoryListResponse> findAll() {
        return ResponseEntity.ok(
                categoryMapper.categoryListToCategoryResponseList(
                        dbCategoryService.findAll()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                categoryMapper.categoryToResponse(dbCategoryService.findById(id))
        );
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> create(@RequestBody @Valid UpsertCategoryRequest request) {
        Category newCategory = dbCategoryService.save(categoryMapper.requestToCategory(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryMapper.categoryToResponse(newCategory));

    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable("id") Long categoryId, @RequestBody @Valid UpsertCategoryRequest request) {
        Category updateCategory = dbCategoryService.update(categoryMapper.requestToCategory(categoryId, request));
        return ResponseEntity.ok(categoryMapper.categoryToResponse(updateCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        dbCategoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
