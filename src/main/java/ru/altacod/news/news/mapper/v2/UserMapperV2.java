package ru.altacod.news.news.mapper.v2;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.altacod.news.news.api.model.UpsertUserRequest;
import ru.altacod.news.news.api.model.UserListResponse;
import ru.altacod.news.news.api.model.UserResponse;
import ru.altacod.news.news.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapperV2 {

    User requestToUser(UpsertUserRequest request);

    @Mapping(source = "userId", target = "id")
    User requestToUser(Long userId, UpsertUserRequest request);

    UserResponse userToResponse(User user);

    default UserListResponse userListToUserResponseList(List<User> users) {
        UserListResponse response = new UserListResponse();
        response.setUsers(users.stream()
                .map(this::userToResponse).collect(Collectors.toList()));
        return response;
    }
}
