package com.sip.ams.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sip.ams.entities.Article;
import com.sip.ams.repositories.ArticleRepository;

@Service
public class ArticleServiceImp implements ArticleService {

    @Autowired
    ArticleRepository articlerepository;

    @Override
    public List<Article> getAllArticles() {
        return (List<Article>) this.articlerepository.findAll();
    }

    @Override
    public Optional<Article> getArticleById(int id) {
        return this.articlerepository.findById(id);
    }

    @Override
    public void deleteArticleById(int id) {
        this.articlerepository.deleteById(id);
    }

    @Override
    public Article saveArticle(Article article) {
        return this.articlerepository.save(article);
    }
}
