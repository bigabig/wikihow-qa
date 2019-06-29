package de.bigabig.wikihowqa.model.rest;

public class SummarizationRequest {

    private String text;
    private String method;

    public SummarizationRequest() {
    }

    public SummarizationRequest(String text, String method) {
        this.text = text;
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
