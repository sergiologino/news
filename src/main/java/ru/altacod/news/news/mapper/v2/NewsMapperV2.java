package ru.altacod.news.news.mapper.v2;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.altacod.news.news.api.model.NewsListResponse;
import ru.altacod.news.news.api.model.NewsResponse;
import ru.altacod.news.news.api.model.UpsertNewsRequest;
import ru.altacod.news.news.model.News;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {CommentMapperV2.class})
public interface NewsMapperV2 {

    News requestToNews(UpsertNewsRequest request);

    @Mapping(source = "newsId", target = "id")
    News requestToNews(Long newsId, UpsertNewsRequest request);

    NewsResponse newsToResponse(News news);

    default NewsListResponse newsListToNewsResponseList(List<News> news) {
        NewsListResponse response = new NewsListResponse();
        response.setNews(news.stream()
                .map(this::newsToResponse).collect(Collectors.toList()));
        return response;
    }
}
