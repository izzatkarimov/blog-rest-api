package com.personal.blog.controller;

import com.personal.blog.dto.ArticleDTO;
import com.personal.blog.dto.CreateArticleDTO;
import com.personal.blog.entity.Article;
import com.personal.blog.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/articles") // Base URL for this controller
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    // Create a new article
    @PostMapping
    public ResponseEntity<ArticleDTO> createArticle(@Valid @RequestBody CreateArticleDTO createArticleDTO) {
        // Convert the CreateArticleDTO to an Article entity using Lombok constructor or builder
        Article article = Article.builder()
                .title(createArticleDTO.getTitle())
                .content(createArticleDTO.getContent())
                .tags(createArticleDTO.getTags())
                .publishedAt(LocalDateTime.now()) // Set the current time for publishedAt
                .build();

        // Call the service layer to save the article
        Article createdArticle = articleService.createArticle(article);

        // Convert the saved article to an ArticleDTO for the response
        ArticleDTO articleDTO = new ArticleDTO(createdArticle.getTitle(), createdArticle.getContent(), createdArticle.getPublishedAt());

        return new ResponseEntity<>(articleDTO, HttpStatus.CREATED);
    }

    // Get all articles
    @GetMapping
    public ResponseEntity<List<ArticleDTO>> getAllArticles() {
        List<Article> articles = articleService.getAllArticles();
        List<ArticleDTO> articleDTOs = articles.stream()
                .map(article -> new ArticleDTO(article.getTitle(), article.getContent(), article.getPublishedAt()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(articleDTOs, HttpStatus.OK);
    }

    // Get article by ID
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable Long id) {
        Optional<Article> article = articleService.getArticleById(id);
        return article.map(value -> new ResponseEntity<>(new ArticleDTO(value.getTitle(), value.getContent(), value.getPublishedAt()), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update an article by ID
    @PutMapping("/{id}")
    public ResponseEntity<ArticleDTO> updateArticle(@PathVariable Long id, @Valid @RequestBody CreateArticleDTO createArticleDTO) {
        // Convert the CreateArticleDTO to an Article entity using Lombok constructor or builder
        Article article = Article.builder()
                .id(id)
                .title(createArticleDTO.getTitle())
                .content(createArticleDTO.getContent())
                .tags(createArticleDTO.getTags())
                .publishedAt(LocalDateTime.now()) // Optional: Set new timestamp or leave as is
                .build();

        // Call the service layer to update the article
        Article updatedArticle = articleService.updateArticle(id, article);

        if (updatedArticle != null) {
            ArticleDTO articleDTO = new ArticleDTO(updatedArticle.getTitle(), updatedArticle.getContent(), updatedArticle.getPublishedAt());
            return new ResponseEntity<>(articleDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete an article by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
    }

    // Get articles by tag (filtering)
    @GetMapping("/filter")
    public ResponseEntity<List<ArticleDTO>> getArticlesByTag(@RequestParam String tag) {
        List<Article> articles = articleService.getArticlesByTag(tag);
        List<ArticleDTO> articleDTOs = articles.stream()
                .map(article -> new ArticleDTO(article.getTitle(), article.getContent(), article.getPublishedAt()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(articleDTOs, HttpStatus.OK);
    }

    // Get paginated articles
    @GetMapping("/paginated")
    public ResponseEntity<Page<ArticleDTO>> getPaginatedArticles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Article> articles = articleService.getPaginatedArticles(pageable);
        Page<ArticleDTO> articleDTOs = articles.map(article -> new ArticleDTO(article.getTitle(), article.getContent(), article.getPublishedAt()));
        return new ResponseEntity<>(articleDTOs, HttpStatus.OK);
    }

    // Get sorted articles
    @GetMapping("/sorted")
    public ResponseEntity<Page<ArticleDTO>> getSortedArticles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "publishedAt") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        Pageable pageable = PageRequest.of(page, size,
                sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
        Page<Article> articles = articleService.getPaginatedArticles(pageable);
        Page<ArticleDTO> articleDTOs = articles.map(article -> new ArticleDTO(article.getTitle(), article.getContent(), article.getPublishedAt()));
        return new ResponseEntity<>(articleDTOs, HttpStatus.OK);
    }
}