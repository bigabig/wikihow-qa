package de.bigabig.wikihowqa.model;

public class WikihowKeyword {
    private String word;
    private double score;

    public WikihowKeyword() {
    }

    public WikihowKeyword(String word, double score) {
        this.word = word;
        this.score = score;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
