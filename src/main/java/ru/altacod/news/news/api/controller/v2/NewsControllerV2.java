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
import ru.altacod.news.news.api.model.ErrorResponse;
import ru.altacod.news.news.api.model.NewsListResponse;
import ru.altacod.news.news.api.model.NewsResponse;
import ru.altacod.news.news.api.model.UpsertNewsRequest;
import ru.altacod.news.news.mapper.v2.NewsMapperV2;
import ru.altacod.news.news.model.News;
import ru.altacod.news.news.model.NewsFilter;
import ru.altacod.news.news.service.NewsService;

@RestController
@Tag(name = "3. Новости")
@RequestMapping("/api/v2/news")
@RequiredArgsConstructor
public class NewsControllerV2 {

    private final NewsService dbNewsService;

    private final NewsMapperV2 newsMapper;


    @GetMapping("/filter")
    public ResponseEntity<NewsListResponse> filterBy(NewsFilter filter) {
        return ResponseEntity.ok(
                newsMapper.newsListToNewsResponseList(dbNewsService.filterBy(
                        filter
                ))
        );
    }

    @Operation(
            summary = "Получить все новости",
            description = "Возвращает все новости без комментариев",
            tags = {"news"}
    )
    @GetMapping
    public ResponseEntity<NewsListResponse> findAll() {
        return ResponseEntity.ok(
                newsMapper.newsListToNewsResponseList(
                        dbNewsService.findAll()
                )
        );
    }

    @Operation(
            summary = "Получить новость по ID",
            description = "Получить новость по ID. Возвращает ID, текст новости и комментарии к ней",
            tags = {"news", "id"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = NewsResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
                    }
            )
    }
    )
    @GetMapping("/{id}")
    public ResponseEntity<NewsResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                newsMapper.newsToResponse(dbNewsService.findById(id))
        );
    }

    @Operation(
            summary = "Создать новость",
            description = "Создать новую новость",
            tags = {"news"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Новость создана успешно",
                    content = {
                            @Content(schema = @Schema(implementation = NewsResponse.class), mediaType = "application/json")
                    }
            ),
    }
    )
    @PostMapping
    public ResponseEntity<NewsResponse> create(@RequestBody @Valid UpsertNewsRequest request) {
        News addNews = dbNewsService.save(newsMapper.requestToNews(request));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newsMapper.newsToResponse(addNews));
    }

    @Operation(
            summary = "Изменить новость по ID",
            description = "Изменить новость по ID. Возвращает ID, текст измененной новости и комментарии к ней",
            tags = {"news", "id"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = NewsResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
                    }
            )
    }
    )
    @PutMapping("/{id}")
    public ResponseEntity<NewsResponse> update(@PathVariable("id") Long newsId, @RequestBody @Valid UpsertNewsRequest request) {
        News updatedNews = dbNewsService.update(newsMapper.requestToNews(newsId, request));

        return ResponseEntity.ok(newsMapper.newsToResponse(updatedNews));
    }

    @Operation(
            summary = "Удаление новости по ID",
            description = "Удаляет новость со всеми комментариями по переданному ID",
            tags = {"news", "id"}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        dbNewsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
