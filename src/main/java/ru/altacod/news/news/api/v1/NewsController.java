package ru.altacod.news.news.api.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.altacod.news.news.api.model.NewsListResponce;
import ru.altacod.news.news.api.model.NewsResponce;
import ru.altacod.news.news.api.model.UpsertNewsRequest;
import ru.altacod.news.news.mapper.v1.NewsMapper;
import ru.altacod.news.news.model.News;
import ru.altacod.news.news.service.NewsService;

@RestController
@RequestMapping("/api/v1/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;

    private final NewsMapper newsMapper;

    @GetMapping
    public ResponseEntity<NewsListResponce> findAll() {
        return ResponseEntity.ok(newsMapper.newsListToNewsListResponce(newsService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsResponce> findById(@PathVariable Long id){
        return ResponseEntity.ok(
                newsMapper.newsToResponce(newsService.findById(id))
        );
    }

    @PostMapping
    public ResponseEntity<NewsResponce> create(@RequestBody UpsertNewsRequest request){
        News newNews = newsService.save(newsMapper.requestToNews(request));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newsMapper.newsToResponce(newNews));
    }

    @PutMapping("/{id}")
    public  ResponseEntity<NewsResponce> update(@PathVariable("id") Long newsId,
                                                @RequestBody UpsertNewsRequest request){
        News updatedNews=newsService.update(newsMapper.requestToNews(newsId,request));

        return ResponseEntity.ok(newsMapper.newsToResponce(updatedNews));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        newsService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
