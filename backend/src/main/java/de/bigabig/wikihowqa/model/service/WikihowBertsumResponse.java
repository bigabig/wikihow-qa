package de.bigabig.wikihowqa.model.service;

public class WikihowBertsumResponse {

    private String result;

    public WikihowBertsumResponse() {
    }

    public WikihowBertsumResponse(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
