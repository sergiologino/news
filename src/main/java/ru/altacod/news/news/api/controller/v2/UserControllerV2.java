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
import ru.altacod.news.news.mapper.v2.UserMapperV2;
import ru.altacod.news.news.model.User;
import ru.altacod.news.news.service.UserService;

@RestController
@Tag(name = "1. Пользователи")
@RequestMapping("/api/v2/user")
@RequiredArgsConstructor
public class UserControllerV2 {

    private final UserService dbUserService;

    private final UserMapperV2 userMapper;

    @Operation(
            summary = "Получить всех пользователей",
            description = "Возвращает список всех пользователей",
            tags = {"user"}
    )
    @GetMapping
    public ResponseEntity<UserListResponse> findAll() {
        return ResponseEntity.ok(
                userMapper.userListToUserResponseList(dbUserService.findAll())
        );
    }

    @Operation(
            summary = "Получить пользователя по ID",
            description = "Получить пользователя по ID. Возвращает пользователя с ID",
            tags = {"user", "id"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Пользователь по ID успешно найден",
                    content = {
                            @Content(schema = @Schema(implementation = NewsResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь по ID не найден",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
                    }
            )
    }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                userMapper.userToResponse(dbUserService.findById(id))
        );
    }

    @Operation(
            summary = "Создать пользователя",
            description = "Создать нового пользователя",
            tags = {"user"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Пользователь создан успешно",
                    content = {
                            @Content(schema = @Schema(implementation = NewsResponse.class), mediaType = "application/json")
                    }
            ),
    }
    )
    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UpsertUserRequest request) {
        User newUser = dbUserService.save(userMapper.requestToUser(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper.userToResponse(newUser));
    }

    @Operation(
            summary = "Изменить пользователя по ID",
            description = "Изменить пользователя по ID. Возвращает ID и измененного пользователя",
            tags = {"user", "id"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Пользователь изменен успешно",
                    content = {
                            @Content(schema = @Schema(implementation = NewsResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Не найден пользователь по ID ",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
                    }
            )
    }
    )
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable("id") Long userId, @RequestBody @Valid UpsertUserRequest request) {
        User updateUser = dbUserService.update(userMapper.requestToUser(userId, request));
        return ResponseEntity.ok(userMapper.userToResponse(updateUser));
    }
}
