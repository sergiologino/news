package ru.altacod.news.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.altacod.news.api.model.UpsertUserRequest;
import ru.altacod.news.api.model.UserListResponse;
import ru.altacod.news.api.model.UserResponse;
import ru.altacod.news.mapper.UserMapper;
import ru.altacod.news.model.User;
import ru.altacod.news.security.annotations.CanAccessUser;
import ru.altacod.news.security.annotations.CanDeleteUser;
import ru.altacod.news.security.annotations.CanUpdateUser;
import ru.altacod.news.service.UserService;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<UserListResponse> findAll() {
        return ResponseEntity.ok(userMapper.userListToResponce(userService.findAll()));
    }

    @CanAccessUser
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userMapper.userToResponse(userService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UpsertUserRequest request) {
        User newUser = userService.save(userMapper.requestToUser(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.userToResponse(newUser));
    }

    @CanUpdateUser
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable("id") Long userId,
                                               @RequestBody UpsertUserRequest request) {
        User updatedUser = userService.update(userMapper.requestToUser(userId, request));
        return ResponseEntity.ok(userMapper.userToResponse(updatedUser));
    }


    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UpsertUserRequest request) {
        User user = userMapper.requestToUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User newUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.userToResponse(newUser));
    }

    @CanDeleteUser
    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> delete(@PathVariable("id") Long userId,
                                               @RequestBody UpsertUserRequest request) {
        User deletedUser = userService.delete(userMapper.requestToUser(userId, request));
        return ResponseEntity.ok(userMapper.userToResponse(deletedUser));
    }

}
