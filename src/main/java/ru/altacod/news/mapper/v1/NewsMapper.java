package ru.altacod.news.mapper.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.altacod.news.api.model.NewsListResponse;
import ru.altacod.news.api.model.NewsResponse;
import ru.altacod.news.api.model.UpsertNewsRequest;
import ru.altacod.news.model.News;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NewsMapper {
  //  private final UserService userService;
  //  private final CategoryService categoryService;

    public News requestToNews(UpsertNewsRequest request) {
        News news = new News();
        news.setCategoryId(request.getCategoryId());
        news.setDescription(request.getDescription());
        news.setName(request.getName());

        return news;
    }

    public News requestToNews(Long newsId, UpsertNewsRequest request) {
        News news = requestToNews(request);
        news.setId(newsId);
        return news;
    }

    public NewsResponse newsToResponse(News news) {
        NewsResponse newsResponse = new NewsResponse();
        newsResponse.setId(news.getId());
        newsResponse.setCategoryId(news.getCategoryId());
        newsResponse.setName(news.getName());
        newsResponse.setDescription(news.getDescription());


        return newsResponse;
    }

    public List<NewsResponse> newsListToResponceList(List<News> newsList) {
        return newsList.stream()
                .map(this::newsToResponse)
                .collect(Collectors.toList());
    }

    public NewsListResponse newsListToNewsListResponse(List<News> newsList) {
        NewsListResponse response = new NewsListResponse();
        response.setNews(newsListToResponceList(newsList));

        return response;
    }
}
