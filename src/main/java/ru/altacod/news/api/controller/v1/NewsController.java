package ru.altacod.news.api.controller.v1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.altacod.news.api.model.NewsListResponse;
import ru.altacod.news.api.model.NewsResponse;
import ru.altacod.news.api.model.UpsertNewsRequest;
import ru.altacod.news.mapper.v1.NewsMapper;
import ru.altacod.news.model.News;
import ru.altacod.news.model.User;
import ru.altacod.news.security.annotations.CanDeleteNews;
import ru.altacod.news.security.annotations.CanUpdateNews;
import ru.altacod.news.service.NewsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.altacod.news.service.UserService;

@RestController
@RequestMapping("/api/v1/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;
    private final UserService userService;

    private final NewsMapper newsMapper;

    @GetMapping
    public ResponseEntity<NewsListResponse> findAll() {
        return ResponseEntity.ok(newsMapper.newsListToNewsListResponse(newsService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                newsMapper.newsToResponse(newsService.findById(id))
        );
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MODERATOR')")
    @PostMapping
    public ResponseEntity<NewsResponse> create(@RequestBody @Valid UpsertNewsRequest request) {
        String username = getCurrentUsername();
        User user = userService.findAll().stream()
                .filter(u -> u.getName().equals(username))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        News news = newsMapper.requestToNews(request);
        news.setUserId(user.getId());

        News savedNews = newsService.save(news);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newsMapper.newsToResponse(savedNews));
    }

    @CanUpdateNews
    @PutMapping("/{id}")
    public ResponseEntity<NewsResponse> update(@PathVariable("id") Long newsId,
                                               @RequestBody UpsertNewsRequest request) {
        News updatedNews = newsService.update(newsMapper.requestToNews(newsId, request));

        return ResponseEntity.ok(newsMapper.newsToResponse(updatedNews));
    }

    @CanDeleteNews
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        newsService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }


}
