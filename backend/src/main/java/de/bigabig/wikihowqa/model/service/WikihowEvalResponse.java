package de.bigabig.wikihowqa.model.service;

public class WikihowEvalResponse {

    private RougeEvaluation evaluation;
    private String formatted;

    public WikihowEvalResponse() {
    }

    public WikihowEvalResponse(RougeEvaluation evaluation, String formatted) {
        this.evaluation = evaluation;
        this.formatted = formatted;
    }

    public RougeEvaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(RougeEvaluation evaluation) {
        this.evaluation = evaluation;
    }

    public String getFormatted() {
        return formatted;
    }

    public void setFormatted(String formatted) {
        this.formatted = formatted;
    }
}
