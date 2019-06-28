package de.bigabig.wikihowqa.model;

public class WikihowNE {

    private String text;
    private String label;
    private int start;
    private int end;

    public WikihowNE() {
    }

    public WikihowNE(String text, String label, int start, int end) {
        this.text = text;
        this.label = label;
        this.start = start;
        this.end = end;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
