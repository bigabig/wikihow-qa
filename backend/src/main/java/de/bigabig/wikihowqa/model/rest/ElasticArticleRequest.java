package de.bigabig.wikihowqa.model.rest;

public class ElasticArticleRequest {

    private String topic;
    private int count;
    private String elasticindex;

    public ElasticArticleRequest() {
    }

    public ElasticArticleRequest(String topic, int count, String elasticindex) {
        this.topic = topic;
        this.count = count;
        this.elasticindex = elasticindex;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getElasticindex() {
        return elasticindex;
    }

    public void setElasticindex(String elasticindex) {
        this.elasticindex = elasticindex;
    }
}
