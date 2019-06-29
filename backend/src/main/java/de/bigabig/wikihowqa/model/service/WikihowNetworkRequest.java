package de.bigabig.wikihowqa.model.service;

import com.google.gson.annotations.SerializedName;

public class WikihowNetworkRequest {

    @SerializedName("abstract")
    private String text_abstract;

    private String article;

    public WikihowNetworkRequest() {
    }

    public WikihowNetworkRequest(String text_abstract, String article) {
        this.text_abstract = text_abstract;
        this.article = article;
    }

    @SerializedName("abstract")
    public String getText_abstract() {
        return text_abstract;
    }

    public void setText_abstract(String text_abstract) {
        this.text_abstract = text_abstract;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }
}
