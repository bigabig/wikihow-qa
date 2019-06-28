package de.bigabig.wikihowqa.model;

public class WikihowNERRequest {

    private String text;

    public WikihowNERRequest() {
    }

    public WikihowNERRequest(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
