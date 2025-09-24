package com.sip.ams.services;

import java.util.List;
import java.util.Optional;

import com.sip.ams.entities.Article;

public interface ArticleService {
    public List<Article> getAllArticles();
    public Optional<Article> getArticleById(int id);
    public void deleteArticleById(int id);
    public Article saveArticle(Article article);
}
