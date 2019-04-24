package de.bigabig.wikihowqa.model;

public class SearchQuery {

    private String topic;

    public SearchQuery() {
    }

    public SearchQuery(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
