package com.personal.blog.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArticleDTO {

    private String title;
    private String content;
    private LocalDateTime publishedAt;

}