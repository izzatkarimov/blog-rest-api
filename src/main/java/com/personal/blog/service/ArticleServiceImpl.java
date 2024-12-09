package com.personal.blog.service;

import com.personal.blog.entity.Article;
import com.personal.blog.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Article createArticle(Article article) {
        return articleRepository.save(article); // Saves the article to the database
    }

    @Override
    public List<Article> getAllArticles() {
        return articleRepository.findAll(); // Retrieves all articles
    }

    @Override
    public Optional<Article> getArticleById(Long id) {
        return articleRepository.findById(id); // Retrieves article by ID
    }

    @Override
    public Article updateArticle(Long id, Article article) {
        if (articleRepository.existsById(id)) {
            article.setId(id);
            return articleRepository.save(article); // Updates the article if it exists
        } else {
            return null; // Or throw an exception, based on your design
        }
    }

    @Override
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id); // Deletes article by ID
    }

    @Override
    public List<Article> getArticlesByTag(String tag) {
        return articleRepository.findAll()
                .stream()
                .filter(article -> article.getTags() != null && article.getTags().contains(tag))
                .collect(Collectors.toList());
    }

    @Override
    public Page<Article> getPaginatedArticles(Pageable pageable) {
        return articleRepository.findAll(pageable); // Retrieves articles with pagination
    }

}
