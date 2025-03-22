package ru.altacod.news.security.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.altacod.news.model.Comment;
import ru.altacod.news.model.User;
import ru.altacod.news.service.CommentService;
import ru.altacod.news.service.UserService;
import ru.altacod.news.security.annotations.CanDeleteComment;
import ru.altacod.news.security.annotations.CanUpdateComment;

@Aspect
@Component
@RequiredArgsConstructor
public class CommentAccessAspect {

    private final CommentService commentService;
    private final UserService userService;

    private boolean isOwner(Long commentId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userService.findAll().stream()
                .filter(u -> u.getName().equals(username))
                .findFirst()
                .orElseThrow();

        Comment comment = commentService.findById(commentId);
        return comment.getUserId().equals(currentUser.getId());
    }

    private boolean isAdminOrModerator() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_MODERATOR"));
    }

    @Before("@annotation(ru.altacod.news.security.annotations.CanUpdateComment) && args(commentId,..)")
    public void checkUpdate(Long commentId) {
        if (!isOwner(commentId)) {
            throw new SecurityException("Вы не можете редактировать чужой комментарий");
        }
    }

    @Before("@annotation(ru.altacod.news.security.annotations.CanDeleteComment) && args(commentId)")
    public void checkDelete(Long commentId) {
        if (!isOwner(commentId) && !isAdminOrModerator()) {
            throw new SecurityException("Вы не можете удалить чужой комментарий");
        }
    }
}

