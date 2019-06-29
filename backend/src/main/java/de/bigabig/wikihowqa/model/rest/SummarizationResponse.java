package de.bigabig.wikihowqa.model.rest;

public class SummarizationResponse {

    private String summary;

    public SummarizationResponse() {
    }

    public SummarizationResponse(String summary) {
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
