package ru.altacod.news.security.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.altacod.news.service.UserService;
import ru.altacod.news.model.User;
import ru.altacod.news.security.annotations.*;

import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor
public class UserAccessAspect {

    private final UserService userService;

    private static final String ROLE_USER = "ROLE_USER";

    private boolean isCurrentUserOrHasHigherRole(Long userId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User currentUser = userService.findAll().stream()
                .filter(u -> u.getName().equals(username))
                .findFirst()
                .orElse(null);

        if (currentUser == null) return false;

        boolean isAdminOrModerator = currentUser.getRoles().stream()
                .anyMatch(r -> r.equals("ROLE_ADMIN") || r.equals("ROLE_MODERATOR"));

        boolean isSelf = currentUser.getId().equals(userId);

        if (currentUser.getRoles().contains(ROLE_USER) && !isSelf) {
            return false;
        }

        return isSelf || isAdminOrModerator;
    }

    @Before("@annotation(ru.altacod.news.security.annotations.CanAccessUser) && args(id,..)")
    public void checkAccess(Long id) {
        if (!isCurrentUserOrHasHigherRole(id)) {
            throw new SecurityException("Нет доступа к просмотру пользователя");
        }
    }

    @Before("@annotation(ru.altacod.news.security.annotations.CanUpdateUser) && args(id,..)")
    public void checkUpdate(Long id) {
        if (!isCurrentUserOrHasHigherRole(id)) {
            throw new SecurityException("Нет доступа к обновлению пользователя");
        }
    }

    @Before("@annotation(ru.altacod.news.security.annotations.CanDeleteUser) && args(id)")
    public void checkDelete(Long id) {
        if (!isCurrentUserOrHasHigherRole(id)) {
            throw new SecurityException("Нет доступа к удалению пользователя");
        }
    }
}
