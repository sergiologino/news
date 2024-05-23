package ru.altacod.news.news.mapper.v2;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.altacod.news.news.api.model.NewsForListResponse;
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

    public default NewsForListResponse newsForListToResponse(News news) {
        if (news == null) {
            return null;
        }

        NewsForListResponse newsForListResponse = new NewsForListResponse();

        newsForListResponse.setId(news.getId());
        newsForListResponse.setName(news.getName());
        newsForListResponse.setDescription(news.getDescription());
        newsForListResponse.setUserId(news.getUserId());
        newsForListResponse.setCategoryId(news.getCategoryId());
        newsForListResponse.setCommentQuantity(news.getComments().size());

        return newsForListResponse;
    }

    default NewsForListResponse newsListToNewsResponseList(List<News> news) {
        NewsForListResponse response = new NewsForListResponse();
        response.setNews(news.stream().map(this::newsForListToResponse).collect(Collectors.toList()));
        return response;
    }

}
