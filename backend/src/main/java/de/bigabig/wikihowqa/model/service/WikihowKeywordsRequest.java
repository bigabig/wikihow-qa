package de.bigabig.wikihowqa.model.service;

public class WikihowKeywordsRequest {

    private String text;
    private String lang;
    private int count;

    public WikihowKeywordsRequest() {}

    public WikihowKeywordsRequest(String text, String lang, int count) {
        this.text = text;
        this.lang = lang;
        this.count = count;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
