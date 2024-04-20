package ru.altacod.news.news.api.controller.v2;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.altacod.news.news.api.model.UpsertUserRequest;
import ru.altacod.news.news.api.model.UserListResponse;
import ru.altacod.news.news.api.model.UserResponse;
import ru.altacod.news.news.mapper.v2.UserMapperV2;
import ru.altacod.news.news.model.User;
import ru.altacod.news.news.service.UserService;

@RestController
@RequestMapping("/api/v2/user")
@RequiredArgsConstructor
public class UserControllerV2 {

    private final UserService dbUserService;

    private final UserMapperV2 userMapper;

    @GetMapping
    public ResponseEntity<UserListResponse> findAll() {
        return ResponseEntity.ok(
                userMapper.userListToUserResponseList(dbUserService.findAll())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                userMapper.userToResponse(dbUserService.findById(id))
        );
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UpsertUserRequest request) {
        User newUser = dbUserService.save(userMapper.requestToUser(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper.userToResponse(newUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable("id") Long userId, @RequestBody @Valid UpsertUserRequest request) {
        User updateUser = dbUserService.update(userMapper.requestToUser(userId, request));
        return ResponseEntity.ok(userMapper.userToResponse(updateUser));
    }
}
