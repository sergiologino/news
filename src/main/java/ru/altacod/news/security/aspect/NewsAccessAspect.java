package ru.altacod.news.security.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.altacod.news.model.News;
import ru.altacod.news.model.User;
import ru.altacod.news.service.NewsService;
import ru.altacod.news.service.UserService;

@Aspect
@Component
@RequiredArgsConstructor
public class NewsAccessAspect {

    private final NewsService newsService;
    private final UserService userService;

    private boolean isOwner(Long newsId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userService.findAll().stream()
                .filter(u -> u.getName().equals(username))
                .findFirst()
                .orElseThrow();

        News news = newsService.findById(newsId);

        return news.getUserId().equals(currentUser.getId());
    }

    private boolean isAdminOrModerator() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_MODERATOR"));
    }

    @Before("@annotation(ru.altacod.news.security.annotations.CanUpdateNews) && args(newsId,..)")
    public void checkUpdate(Long newsId) {
        if (!isOwner(newsId)) {
            throw new SecurityException("Вы не можете редактировать чужую новость");
        }
    }

    @Before("@annotation(ru.altacod.news.security.annotations.CanDeleteNews) && args(newsId)")
    public void checkDelete(Long newsId) {
        if (!isOwner(newsId) && !isAdminOrModerator()) {
            throw new SecurityException("Вы не можете удалить чужую новость");
        }
    }
}

