package ru.altacod.news.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.altacod.news.news.model.Comment;

public interface DbCommentRepository extends JpaRepository<Comment, Long> {

}
