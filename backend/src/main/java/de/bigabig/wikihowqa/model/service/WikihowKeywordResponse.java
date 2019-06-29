package de.bigabig.wikihowqa.model.service;

import java.util.List;

public class WikihowKeywordResponse {

    private List<WikihowKeyword> keywords;

    public WikihowKeywordResponse() {
    }

    public WikihowKeywordResponse(List<WikihowKeyword> keywords) {
        this.keywords = keywords;
    }

    public List<WikihowKeyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<WikihowKeyword> keywords) {
        this.keywords = keywords;
    }
}
