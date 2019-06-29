package de.bigabig.wikihowqa.model.rest;

import de.bigabig.wikihowqa.model.service.WikihowArticle;

import java.util.List;

public class ElasticArticleResponse {

    private List<WikihowArticle> articles;

    public ElasticArticleResponse() {
    }

    public ElasticArticleResponse(List<WikihowArticle> articles) {
        this.articles = articles;
    }

    public List<WikihowArticle> getArticles() {
        return articles;
    }

    public void setArticles(List<WikihowArticle> articles) {
        this.articles = articles;
    }
}
