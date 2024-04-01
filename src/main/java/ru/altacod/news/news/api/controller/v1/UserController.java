package ru.altacod.news.news.api.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.altacod.news.news.api.model.UpsertUserRequest;
import ru.altacod.news.news.api.model.UserListResponse;
import ru.altacod.news.news.api.model.UserResponse;
import ru.altacod.news.news.mapper.v1.UserMapper;
import ru.altacod.news.news.model.User;
import ru.altacod.news.news.service.UserService;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<UserListResponse> findAll() {
        return ResponseEntity.ok(userMapper.userListToResponce(userService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userMapper.userToResponse(userService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UpsertUserRequest request) {
        User newUser = userService.save(userMapper.requestToUser(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.userToResponse(newUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable("id") Long userId,
                                               @RequestBody UpsertUserRequest request) {
        User updatedUser = userService.update(userMapper.requestToUser(userId, request));
        return ResponseEntity.ok(userMapper.userToResponse(updatedUser));
    }
}
