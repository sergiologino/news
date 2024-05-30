package ru.altacod.news.news.repository;

import org.springframework.data.jpa.domain.Specification;
import ru.altacod.news.news.model.News;
import ru.altacod.news.news.model.NewsFilter;

public interface NewsSpecification {
    static Specification<News> withFilter(NewsFilter newsFilter) {
        return Specification.where(byCategoryId(newsFilter.getCategoryId()))
                .and((byUserId(newsFilter.getUserId())));
    }

    static Specification<News> byCategoryId(Long categoryId) {
        return (root, query, cb) -> {
            if (categoryId == null) {
                return null;
            }
            return cb.equal(root.get("categoryId"), categoryId);
        };

    }

    static Specification<News> byUserId(Long userId) {
        return (root, query, cb) -> {
            if (userId == null) {
                return null;
            }
            return cb.equal(root.get("userId"), userId);
        };
    }
}
