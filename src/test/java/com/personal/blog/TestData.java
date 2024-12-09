package com.personal.blog;

import com.personal.blog.entity.Article;

import java.time.LocalDateTime;

public final class TestData {

    private TestData() {
    }

    public static Article testArticle() {
        Article article = new Article();
        article.setTitle("Testing");
        article.setContent("testing");
        article.setTags("testing, UnitTesting");
        article.setPublishedAt(LocalDateTime.now());
        return article;
    }
}
