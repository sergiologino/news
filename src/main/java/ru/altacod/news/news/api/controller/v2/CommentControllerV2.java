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
import ru.altacod.news.news.mapper.v2.CommentMapperV2;
import ru.altacod.news.news.model.Comment;
import ru.altacod.news.news.service.CommentService;

@RestController
@Tag(name = "4. Комментарии")
@RequestMapping("/api/v2/comment")
@RequiredArgsConstructor
public class CommentControllerV2 {

    private final CommentService dbCommentService;

    private final CommentMapperV2 commentMapper;

    @Operation(
            summary = "Получить все комментарии",
            description = "Возвращает все комментарии",
            tags = {"comment"}
    )
    @GetMapping
    public ResponseEntity<CommentListResponse> findAll() {
        return ResponseEntity.ok(
                commentMapper.commentListToCommentListResponse(
                        dbCommentService.findAll()
                )
        );
    }

    @Operation(
            summary = "Получить комментарий по ID",
            description = "Получить комментарий по ID. Возвращает ID, текст комментария",
            tags = {"comment", "id"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Комментарий успешно найден",
                    content = {
                            @Content(schema = @Schema(implementation = NewsResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Комментарий не найден",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
                    }
            )
    }
    )
    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                commentMapper.commentToResponse(dbCommentService.findById(id))
        );
    }

    @Operation(
            summary = "Создать комментарий",
            description = "Создать новый комментарий",
            tags = {"comment"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Комментарий создан успешно",
                    content = {
                            @Content(schema = @Schema(implementation = NewsResponse.class), mediaType = "application/json")
                    }
            ),
    }
    )
    @PostMapping
    public ResponseEntity<CommentResponse> create(@RequestBody @Valid UpsertCommentRequest request) {
        Comment newComment = dbCommentService.save(commentMapper.requestToComment(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentMapper.commentToResponse(newComment));
    }

    @Operation(
            summary = "Изменить комментарий по ID",
            description = "Изменить комментарий по ID. Возвращает ID, текст измененного комментария",
            tags = {"comment", "id"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Комментарий изменен успешно",
                    content = {
                            @Content(schema = @Schema(implementation = NewsResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Не удалось изменить комментарий",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
                    }
            )
    }
    )
    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> update(@PathVariable("id") Long commentId, @RequestBody @Valid UpsertCommentRequest request) {
        Comment updatedComment = dbCommentService.update(commentMapper.requestToComment(commentId, request));
        return ResponseEntity.ok(commentMapper.commentToResponse(updatedComment));
    }

    @Operation(
            summary = "Удаление комментария по ID",
            description = "Удаляет Комментарий по переданному ID",
            tags = {"comment", "id"}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        dbCommentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
