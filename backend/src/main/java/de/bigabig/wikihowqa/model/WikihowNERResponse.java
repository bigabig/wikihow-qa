package de.bigabig.wikihowqa.model;

import java.util.List;

public class WikihowNERResponse {

    private List<WikihowNE> entities;

    public WikihowNERResponse() {
    }

    public WikihowNERResponse(List<WikihowNE> entities) {
        this.entities = entities;
    }

    public List<WikihowNE> getEntities() {
        return entities;
    }

    public void setEntities(List<WikihowNE> entities) {
        this.entities = entities;
    }
}
