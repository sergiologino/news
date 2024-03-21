package ru.altacod.news.news.mapper.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.altacod.news.news.api.model.NewsListResponce;
import ru.altacod.news.news.api.model.NewsResponce;
import ru.altacod.news.news.api.model.UpsertNewsRequest;
import ru.altacod.news.news.model.News;
import ru.altacod.news.news.service.CategoryService;
import ru.altacod.news.news.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NewsMapper {
  //  private final UserService userService;
  //  private final CategoryService categoryService;

    public News requestToNews(UpsertNewsRequest request){
        News news=new News();
        news.setCategoryId(request.getCategoryId());
        news.setDescription(request.getDescription());
        news.setName(request.getName());
        news.setUserId(request.getUserId());
        return news;
    }

    public News requestToNews(Long newsId, UpsertNewsRequest request){
        News news= requestToNews(request);
        news.setId(newsId);
        return news;
    }

    public NewsResponce newsToResponce(News news){
        NewsResponce newsResponce = new NewsResponce();
        newsResponce.setId(news.getId());
        newsResponce.setCategoryId(news.getCategoryId());
        newsResponce.setName(news.getName());
        newsResponce.setDescription(news.getDescription());
        newsResponce.setUserId(news.getUserId());

        return newsResponce;
    }
    public List<NewsResponce> newsListToResponceList(List<News> newsList){
        return newsList.stream()
                .map(this::newsToResponce)
                .collect(Collectors.toList());
    }
    public NewsListResponce newsListToNewsListResponce(List<News> newsList){
        NewsListResponce responce=new NewsListResponce();
        responce.setNews(newsListToResponceList(newsList));

        return responce;
    }
}
