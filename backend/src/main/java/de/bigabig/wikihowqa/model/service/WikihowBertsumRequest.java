package de.bigabig.wikihowqa.model.service;

public class WikihowBertsumRequest {

    private String text;

    public WikihowBertsumRequest() {}

    public WikihowBertsumRequest(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
