package de.bigabig.wikihowqa.model.service;

import java.util.List;

public class WikihowArticle {

    private String title;
    private List summary;
    private String summary_string;
    private String article;
    private String filename;
    private float score;

    public WikihowArticle() {
    }

    public WikihowArticle(String title, List summary, String summary_string, String article, String filename) {
        this.title = title;
        this.summary = summary;
        this.summary_string = summary_string;
        this.article = article;
        this.filename = filename;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List getSummary() {
        return summary;
    }

    public void setSummary(List summary) {
        this.summary = summary;
    }

    public String getSummary_string() {
        return summary_string;
    }

    public void setSummary_string(String summary_string) {
        this.summary_string = summary_string;
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
}
