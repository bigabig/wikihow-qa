package de.bigabig.wikihowqa.model.service;

public class WikihowArticle {

    private String title;
    private String suggest_title;
    private String summary;
    private String article;
    private String full_article;
    private String filename;
    private float score;

    public WikihowArticle() {
    }

    public WikihowArticle(String title, String summary, String article, String full_article, String filename) {
        this.title = title;
        this.summary = summary;
        this.article = article;
        this.filename = filename;
        this.full_article = full_article;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getSuggest_title() {
        return suggest_title;
    }

    public void setSuggest_title(String suggest_title) {
        this.suggest_title = suggest_title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getFull_article() {
        return full_article;
    }

    public void setFull_article(String full_article) {
        this.full_article = full_article;
    }
}
