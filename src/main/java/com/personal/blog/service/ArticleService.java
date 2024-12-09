package com.personal.blog.service;

import com.personal.blog.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    // Creates a New Article
    Article createArticle(Article article);

    // Returns a list of Article objects, representing all the articles
    List<Article> getAllArticles();

    // Returns an Optional<Article> which either contains the article if found or is empty if no article exists with the given id.
    Optional<Article> getArticleById(Long id);

    // Returns an Updated Article Object
    Article updateArticle(Long id, Article article);

    // Returns Nothing - Deletes an Articles
    void deleteArticle(Long id);

    // Returns a list of Article objects that have the specified tag
    List<Article> getArticlesByTag(String tag);

    // Returns  A Page<Article> object, which includes a subset of articles and additional pagination metadata
    Page<Article> getPaginatedArticles(Pageable pageable);
}