package ru.altacod.news.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.altacod.news.api.model.UpsertUserRequest;
import ru.altacod.news.api.model.UserListResponse;
import ru.altacod.news.api.model.UserResponse;
import ru.altacod.news.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {
    public User requestToUser(UpsertUserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setPassword(request.getPassword());
        user.setRoles(request.getRoles());
        return user;
    }

    public User requestToUser(Long userId, UpsertUserRequest request) {
        User user = requestToUser(request);
        user.setId(userId);
        user.setRoles(request.getRoles());
        return user;
    }

    public UserResponse userToResponse(User user) {
        UserResponse userResponse = new UserResponse();

        userResponse.setName(user.getName());
        userResponse.setPassword(user.getPassword());
        userResponse.setRoles(user.getRoles());
        return userResponse;
    }

    public List<UserResponse> userListToResponceList(List<User> users) {
        return users.stream().map(this::userToResponse).collect(Collectors.toList());

    }

    public UserListResponse userListToResponce(List<User> users) {
        UserListResponse response = new UserListResponse();
        response.setUsers(userListToResponceList(users));
        return response;
    }
}
