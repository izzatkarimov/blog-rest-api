package com.personal.blog.repository;

import com.personal.blog.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    // Method to support pagination
    Page<Article> findAll(Pageable pageable);
}