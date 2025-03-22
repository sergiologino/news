package ru.altacod.news.news.api.controller.v2;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.altacod.news.news.api.model.*;
import ru.altacod.news.news.mapper.v2.CategoryMapperV2;
import ru.altacod.news.news.model.Category;
import ru.altacod.news.news.service.CategoryService;

@RestController
@Tag(name = "2. Категории")
@RequestMapping("/api/v2/category")
@RequiredArgsConstructor
public class CategoryControllerV2 {

    private final CategoryService dbCategoryService;

    private final CategoryMapperV2 categoryMapper;

    @Operation(
            summary = "Получить все категории",
            description = "Возвращает список всех категорий",
            tags = {"category"}
    )
    @GetMapping
    public ResponseEntity<CategoryListResponse> findAll() {
        return ResponseEntity.ok(
                categoryMapper.categoryListToCategoryResponseList(
                        dbCategoryService.findAll()
                )
        );
    }

    @Operation(
            summary = "Получить категорию по ID",
            description = "Получить категорию по ID. Возвращает категорию с ID",
            tags = {"category", "id"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Категория по ID успешно найдена",
                    content = {
                            @Content(schema = @Schema(implementation = NewsResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Категория по ID не найдена",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
                    }
            )
    }
    )
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                categoryMapper.categoryToResponse(dbCategoryService.findById(id))
        );
    }

    @Operation(
            summary = "Создать категорию",
            description = "Создать новую категорию",
            tags = {"category"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Категория создана успешно",
                    content = {
                            @Content(schema = @Schema(implementation = NewsResponse.class), mediaType = "application/json")
                    }
            ),
    }
    )
    @PostMapping
    public ResponseEntity<CategoryResponse> create(@RequestBody @Valid UpsertCategoryRequest request) {
        Category newCategory = dbCategoryService.save(categoryMapper.requestToCategory(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryMapper.categoryToResponse(newCategory));

    }

    @Operation(
            summary = "Изменить категорию по ID",
            description = "Изменить категорию по ID. Возвращает ID и измененную категорию",
            tags = {"category", "id"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Категория изменена успешно",
                    content = {
                            @Content(schema = @Schema(implementation = NewsResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Не найдена категория по ID ",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
                    }
            )
    }
    )
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable("id") Long categoryId, @RequestBody @Valid UpsertCategoryRequest request) {
        Category updateCategory = dbCategoryService.update(categoryMapper.requestToCategory(categoryId, request));
        return ResponseEntity.ok(categoryMapper.categoryToResponse(updateCategory));
    }

    @Operation(
            summary = "Удаление категории по ID",
            description = "Удаляет категорию по переданному ID",
            tags = {"category", "id"}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        dbCategoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
