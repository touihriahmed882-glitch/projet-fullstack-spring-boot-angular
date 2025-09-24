package com.sip.ams.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sip.ams.entities.Article;
import com.sip.ams.services.ArticleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @Operation(summary = "Récupération de tous les articles", description = "Retourne la liste complète des articles enregistrés dans la base de données.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des articles récupérée avec succès"),
            @ApiResponse(responseCode = "500", description = "Erreur lors de la récupération des articles") })
    @GetMapping("/")
    public ResponseEntity<List<Article>> getAllArticles() {
        return new ResponseEntity<>(this.articleService.getAllArticles(), HttpStatus.OK);
    }

    @Operation(summary = "Ajout d’un nouvel article", description = "Ajoute un nouvel article dans la base de données.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Insertion avec succès"),
            @ApiResponse(responseCode = "500", description = "Problème lors de l'insertion de l'article") })
    @PostMapping("/")
    public ResponseEntity<Article> saveArticle(@RequestBody Article a) {
        return new ResponseEntity<>(this.articleService.saveArticle(a), HttpStatus.CREATED);
    }

    @Operation(summary = "Récupérer un Article par ID", description = "Retourne un article par son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Récupération avec succès"),
            @ApiResponse(responseCode = "404", description = "Article inexistant") })
    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable int id) {
        Optional<Article> opt = this.articleService.getArticleById(id);
        if (opt.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(opt.get(), HttpStatus.OK);
    }

    @Operation(summary = "Suppression d’un article par ID", description = "Supprime un article par son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Suppression avec succès"),
            @ApiResponse(responseCode = "404", description = "Article inexistant") })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticleById(@PathVariable int id) {
        Optional<Article> opt = this.articleService.getArticleById(id);
        if (opt.isEmpty())
            return ResponseEntity.notFound().build();
        else {
            this.articleService.deleteArticleById(id);
            return ResponseEntity.noContent().build();
        }
    }

    @Operation(summary = "Mise à jour d’un article", description = "Met à jour un article existant.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mise à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Article inexistant") })
    @PutMapping("/")
    public ResponseEntity<Article> updateArticle(@RequestBody Article article) {
        Optional<Article> opt = this.articleService.getArticleById(article.getId());
        if (opt.isEmpty())
            return ResponseEntity.notFound().build();
        else {
            Article savedArticle = opt.get();
            savedArticle.setLibelle(article.getLibelle());
            savedArticle.setPrice(article.getPrice());
            savedArticle.setProvider(article.getProvider());
            return new ResponseEntity<>(this.articleService.saveArticle(savedArticle), HttpStatus.OK);
        }
    }
}
