package de.bigabig.wikihowqa.model.service;

public class WikihowEvalRequest {

    private String summary;
    private String gold;

    public WikihowEvalRequest() {
    }

    public WikihowEvalRequest(String summary, String gold) {
        this.summary = summary;
        this.gold = gold;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getGold() {
        return gold;
    }

    public void setGold(String gold) {
        this.gold = gold;
    }
}
