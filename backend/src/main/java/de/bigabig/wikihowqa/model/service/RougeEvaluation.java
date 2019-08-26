package de.bigabig.wikihowqa.model.service;

import com.google.gson.annotations.SerializedName;

public class RougeEvaluation {
    private double rouge_1_recall;
    private double rouge_1_recall_cb;
    private double rouge_1_recall_ce;
    private double rouge_1_precision;
    private double rouge_1_precision_cb;
    private double rouge_1_precision_ce;
    private double rouge_1_f_score;
    private double rouge_1_f_score_cb;
    private double rouge_1_f_score_ce;
    private double rouge_2_recall;
    private double rouge_2_recall_cb;
    private double rouge_2_recall_ce;
    private double rouge_2_precision;
    private double rouge_2_precision_cb;
    private double rouge_2_precision_ce;
    private double rouge_2_f_score;
    private double rouge_2_f_score_cb;
    private double rouge_2_f_score_ce;
    private double rouge_3_recall;
    private double rouge_3_recall_cb;
    private double rouge_3_recall_ce;
    private double rouge_3_precision;
    private double rouge_3_precision_cb;
    private double rouge_3_precision_ce;
    private double rouge_3_f_score;
    private double rouge_3_f_score_cb;
    private double rouge_3_f_score_ce;
    private double rouge_4_recall;
    private double rouge_4_recall_cb;
    private double rouge_4_recall_ce;
    private double rouge_4_precision;
    private double rouge_4_precision_cb;
    private double rouge_4_precision_ce;
    private double rouge_4_f_score;
    private double rouge_4_f_score_cb;
    private double rouge_4_f_score_ce;
    private double rouge_l_recall;
    private double rouge_l_recall_cb;
    private double rouge_l_recall_ce;
    private double rouge_l_precision;
    private double rouge_l_precision_cb;
    private double rouge_l_precision_ce;
    private double rouge_l_f_score;
    private double rouge_l_f_score_cb;
    private double rouge_l_f_score_ce;

    @SerializedName("rouge_w_1.2_recall")
    private double rouge_w_12_recall;

    @SerializedName("rouge_w_1.2_recall_cb")
    private double rouge_w_12_recall_cb;

    @SerializedName("ouge_w_1.2_recall_ce")
    private double rouge_w_12_recall_ce;

    @SerializedName("rouge_w_1.2_precision")
    private double rouge_w_12_precision;

    @SerializedName("rouge_w_1.2_precision_cb")
    private double rouge_w_12_precision_cb;

    @SerializedName("rouge_w_1.2_precision_ce")
    private double rouge_w_12_precision_ce;

    @SerializedName("rouge_w_1.2_f_score")
    private double rouge_w_12_f_score;

    @SerializedName("rouge_w_1.2_f_score_cb")
    private double rouge_w_12_f_score_cb;

    @SerializedName("rouge_w_1.2_f_score_ce")
    private double rouge_w_12_f_score_ce;

    @SerializedName("rouge_s*_recall")
    private double rouge_s_star_recall;

    @SerializedName("rouge_s*_recall_cb")
    private double rouge_s_star_recall_cb;

    @SerializedName("rouge_s*_recall_ce")
    private double rouge_s_star_recall_ce;

    @SerializedName("rouge_s*_precision")
    private double rouge_s_star_precision;

    @SerializedName("rouge_s*_precision_cb")
    private double rouge_s_star_precision_cb;

    @SerializedName("rouge_s*_precision_ce")
    private double rouge_s_star_precision_ce;

    @SerializedName("rouge_s*_f_score")
    private double rouge_s_star_f_score;

    @SerializedName("rouge_s*_f_score_cb")
    private double rouge_s_star_f_score_cb;

    @SerializedName("ouge_s*_f_score_ce")
    private double rouge_s_star_f_score_ce;

    @SerializedName("rouge_su*_recall")
    private double rouge_su_star_recall;

    @SerializedName("rouge_su*_recall_cb")
    private double rouge_su_star_recall_cb;

    @SerializedName("rouge_su*_recall_ce")
    private double rouge_su_star_recall_ce;

    @SerializedName("rouge_su*_precision")
    private double rouge_su_star_precision;

    @SerializedName("rouge_su*_precision_cb")
    private double rouge_su_star_precision_cb;

    @SerializedName("rouge_su*_precision_ce")
    private double rouge_su_star_precision_ce;

    @SerializedName("rouge_su*_f_score")
    private double rouge_su_star_f_score;

    @SerializedName("rouge_su*_f_score_cb")
    private double rouge_su_star_f_score_cb;

    @SerializedName("rouge_su*_f_score_ce")
    private double rouge_su_star_f_score_ce;

    public RougeEvaluation() {
    }

    public double getRouge_1_recall() {
        return rouge_1_recall;
    }

    public void setRouge_1_recall(double rouge_1_recall) {
        this.rouge_1_recall = rouge_1_recall;
    }

    public double getRouge_1_recall_cb() {
        return rouge_1_recall_cb;
    }

    public void setRouge_1_recall_cb(double rouge_1_recall_cb) {
        this.rouge_1_recall_cb = rouge_1_recall_cb;
    }

    public double getRouge_1_recall_ce() {
        return rouge_1_recall_ce;
    }

    public void setRouge_1_recall_ce(double rouge_1_recall_ce) {
        this.rouge_1_recall_ce = rouge_1_recall_ce;
    }

    public double getRouge_1_precision() {
        return rouge_1_precision;
    }

    public void setRouge_1_precision(double rouge_1_precision) {
        this.rouge_1_precision = rouge_1_precision;
    }

    public double getRouge_1_precision_cb() {
        return rouge_1_precision_cb;
    }

    public void setRouge_1_precision_cb(double rouge_1_precision_cb) {
        this.rouge_1_precision_cb = rouge_1_precision_cb;
    }

    public double getRouge_1_precision_ce() {
        return rouge_1_precision_ce;
    }

    public void setRouge_1_precision_ce(double rouge_1_precision_ce) {
        this.rouge_1_precision_ce = rouge_1_precision_ce;
    }

    public double getRouge_1_f_score() {
        return rouge_1_f_score;
    }

    public void setRouge_1_f_score(double rouge_1_f_score) {
        this.rouge_1_f_score = rouge_1_f_score;
    }

    public double getRouge_1_f_score_cb() {
        return rouge_1_f_score_cb;
    }

    public void setRouge_1_f_score_cb(double rouge_1_f_score_cb) {
        this.rouge_1_f_score_cb = rouge_1_f_score_cb;
    }

    public double getRouge_1_f_score_ce() {
        return rouge_1_f_score_ce;
    }

    public void setRouge_1_f_score_ce(double rouge_1_f_score_ce) {
        this.rouge_1_f_score_ce = rouge_1_f_score_ce;
    }

    public double getRouge_2_recall() {
        return rouge_2_recall;
    }

    public void setRouge_2_recall(double rouge_2_recall) {
        this.rouge_2_recall = rouge_2_recall;
    }

    public double getRouge_2_recall_cb() {
        return rouge_2_recall_cb;
    }

    public void setRouge_2_recall_cb(double rouge_2_recall_cb) {
        this.rouge_2_recall_cb = rouge_2_recall_cb;
    }

    public double getRouge_2_recall_ce() {
        return rouge_2_recall_ce;
    }

    public void setRouge_2_recall_ce(double rouge_2_recall_ce) {
        this.rouge_2_recall_ce = rouge_2_recall_ce;
    }

    public double getRouge_2_precision() {
        return rouge_2_precision;
    }

    public void setRouge_2_precision(double rouge_2_precision) {
        this.rouge_2_precision = rouge_2_precision;
    }

    public double getRouge_2_precision_cb() {
        return rouge_2_precision_cb;
    }

    public void setRouge_2_precision_cb(double rouge_2_precision_cb) {
        this.rouge_2_precision_cb = rouge_2_precision_cb;
    }

    public double getRouge_2_precision_ce() {
        return rouge_2_precision_ce;
    }

    public void setRouge_2_precision_ce(double rouge_2_precision_ce) {
        this.rouge_2_precision_ce = rouge_2_precision_ce;
    }

    public double getRouge_2_f_score() {
        return rouge_2_f_score;
    }

    public void setRouge_2_f_score(double rouge_2_f_score) {
        this.rouge_2_f_score = rouge_2_f_score;
    }

    public double getRouge_2_f_score_cb() {
        return rouge_2_f_score_cb;
    }

    public void setRouge_2_f_score_cb(double rouge_2_f_score_cb) {
        this.rouge_2_f_score_cb = rouge_2_f_score_cb;
    }

    public double getRouge_2_f_score_ce() {
        return rouge_2_f_score_ce;
    }

    public void setRouge_2_f_score_ce(double rouge_2_f_score_ce) {
        this.rouge_2_f_score_ce = rouge_2_f_score_ce;
    }

    public double getRouge_3_recall() {
        return rouge_3_recall;
    }

    public void setRouge_3_recall(double rouge_3_recall) {
        this.rouge_3_recall = rouge_3_recall;
    }

    public double getRouge_3_recall_cb() {
        return rouge_3_recall_cb;
    }

    public void setRouge_3_recall_cb(double rouge_3_recall_cb) {
        this.rouge_3_recall_cb = rouge_3_recall_cb;
    }

    public double getRouge_3_recall_ce() {
        return rouge_3_recall_ce;
    }

    public void setRouge_3_recall_ce(double rouge_3_recall_ce) {
        this.rouge_3_recall_ce = rouge_3_recall_ce;
    }

    public double getRouge_3_precision() {
        return rouge_3_precision;
    }

    public void setRouge_3_precision(double rouge_3_precision) {
        this.rouge_3_precision = rouge_3_precision;
    }

    public double getRouge_3_precision_cb() {
        return rouge_3_precision_cb;
    }

    public void setRouge_3_precision_cb(double rouge_3_precision_cb) {
        this.rouge_3_precision_cb = rouge_3_precision_cb;
    }

    public double getRouge_3_precision_ce() {
        return rouge_3_precision_ce;
    }

    public void setRouge_3_precision_ce(double rouge_3_precision_ce) {
        this.rouge_3_precision_ce = rouge_3_precision_ce;
    }

    public double getRouge_3_f_score() {
        return rouge_3_f_score;
    }

    public void setRouge_3_f_score(double rouge_3_f_score) {
        this.rouge_3_f_score = rouge_3_f_score;
    }

    public double getRouge_3_f_score_cb() {
        return rouge_3_f_score_cb;
    }

    public void setRouge_3_f_score_cb(double rouge_3_f_score_cb) {
        this.rouge_3_f_score_cb = rouge_3_f_score_cb;
    }

    public double getRouge_3_f_score_ce() {
        return rouge_3_f_score_ce;
    }

    public void setRouge_3_f_score_ce(double rouge_3_f_score_ce) {
        this.rouge_3_f_score_ce = rouge_3_f_score_ce;
    }

    public double getRouge_4_recall() {
        return rouge_4_recall;
    }

    public void setRouge_4_recall(double rouge_4_recall) {
        this.rouge_4_recall = rouge_4_recall;
    }

    public double getRouge_4_recall_cb() {
        return rouge_4_recall_cb;
    }

    public void setRouge_4_recall_cb(double rouge_4_recall_cb) {
        this.rouge_4_recall_cb = rouge_4_recall_cb;
    }

    public double getRouge_4_recall_ce() {
        return rouge_4_recall_ce;
    }

    public void setRouge_4_recall_ce(double rouge_4_recall_ce) {
        this.rouge_4_recall_ce = rouge_4_recall_ce;
    }

    public double getRouge_4_precision() {
        return rouge_4_precision;
    }

    public void setRouge_4_precision(double rouge_4_precision) {
        this.rouge_4_precision = rouge_4_precision;
    }

    public double getRouge_4_precision_cb() {
        return rouge_4_precision_cb;
    }

    public void setRouge_4_precision_cb(double rouge_4_precision_cb) {
        this.rouge_4_precision_cb = rouge_4_precision_cb;
    }

    public double getRouge_4_precision_ce() {
        return rouge_4_precision_ce;
    }

    public void setRouge_4_precision_ce(double rouge_4_precision_ce) {
        this.rouge_4_precision_ce = rouge_4_precision_ce;
    }

    public double getRouge_4_f_score() {
        return rouge_4_f_score;
    }

    public void setRouge_4_f_score(double rouge_4_f_score) {
        this.rouge_4_f_score = rouge_4_f_score;
    }

    public double getRouge_4_f_score_cb() {
        return rouge_4_f_score_cb;
    }

    public void setRouge_4_f_score_cb(double rouge_4_f_score_cb) {
        this.rouge_4_f_score_cb = rouge_4_f_score_cb;
    }

    public double getRouge_4_f_score_ce() {
        return rouge_4_f_score_ce;
    }

    public void setRouge_4_f_score_ce(double rouge_4_f_score_ce) {
        this.rouge_4_f_score_ce = rouge_4_f_score_ce;
    }

    public double getRouge_l_recall() {
        return rouge_l_recall;
    }

    public void setRouge_l_recall(double rouge_l_recall) {
        this.rouge_l_recall = rouge_l_recall;
    }

    public double getRouge_l_recall_cb() {
        return rouge_l_recall_cb;
    }

    public void setRouge_l_recall_cb(double rouge_l_recall_cb) {
        this.rouge_l_recall_cb = rouge_l_recall_cb;
    }

    public double getRouge_l_recall_ce() {
        return rouge_l_recall_ce;
    }

    public void setRouge_l_recall_ce(double rouge_l_recall_ce) {
        this.rouge_l_recall_ce = rouge_l_recall_ce;
    }

    public double getRouge_l_precision() {
        return rouge_l_precision;
    }

    public void setRouge_l_precision(double rouge_l_precision) {
        this.rouge_l_precision = rouge_l_precision;
    }

    public double getRouge_l_precision_cb() {
        return rouge_l_precision_cb;
    }

    public void setRouge_l_precision_cb(double rouge_l_precision_cb) {
        this.rouge_l_precision_cb = rouge_l_precision_cb;
    }

    public double getRouge_l_precision_ce() {
        return rouge_l_precision_ce;
    }

    public void setRouge_l_precision_ce(double rouge_l_precision_ce) {
        this.rouge_l_precision_ce = rouge_l_precision_ce;
    }

    public double getRouge_l_f_score() {
        return rouge_l_f_score;
    }

    public void setRouge_l_f_score(double rouge_l_f_score) {
        this.rouge_l_f_score = rouge_l_f_score;
    }

    public double getRouge_l_f_score_cb() {
        return rouge_l_f_score_cb;
    }

    public void setRouge_l_f_score_cb(double rouge_l_f_score_cb) {
        this.rouge_l_f_score_cb = rouge_l_f_score_cb;
    }

    public double getRouge_l_f_score_ce() {
        return rouge_l_f_score_ce;
    }

    public void setRouge_l_f_score_ce(double rouge_l_f_score_ce) {
        this.rouge_l_f_score_ce = rouge_l_f_score_ce;
    }

    public double getRouge_w_12_recall() {
        return rouge_w_12_recall;
    }

    public void setRouge_w_12_recall(double rouge_w_12_recall) {
        this.rouge_w_12_recall = rouge_w_12_recall;
    }

    public double getRouge_w_12_recall_cb() {
        return rouge_w_12_recall_cb;
    }

    public void setRouge_w_12_recall_cb(double rouge_w_12_recall_cb) {
        this.rouge_w_12_recall_cb = rouge_w_12_recall_cb;
    }

    public double getRouge_w_12_recall_ce() {
        return rouge_w_12_recall_ce;
    }

    public void setRouge_w_12_recall_ce(double rouge_w_12_recall_ce) {
        this.rouge_w_12_recall_ce = rouge_w_12_recall_ce;
    }

    public double getRouge_w_12_precision() {
        return rouge_w_12_precision;
    }

    public void setRouge_w_12_precision(double rouge_w_12_precision) {
        this.rouge_w_12_precision = rouge_w_12_precision;
    }

    public double getRouge_w_12_precision_cb() {
        return rouge_w_12_precision_cb;
    }

    public void setRouge_w_12_precision_cb(double rouge_w_12_precision_cb) {
        this.rouge_w_12_precision_cb = rouge_w_12_precision_cb;
    }

    public double getRouge_w_12_precision_ce() {
        return rouge_w_12_precision_ce;
    }

    public void setRouge_w_12_precision_ce(double rouge_w_12_precision_ce) {
        this.rouge_w_12_precision_ce = rouge_w_12_precision_ce;
    }

    public double getRouge_w_12_f_score() {
        return rouge_w_12_f_score;
    }

    public void setRouge_w_12_f_score(double rouge_w_12_f_score) {
        this.rouge_w_12_f_score = rouge_w_12_f_score;
    }

    public double getRouge_w_12_f_score_cb() {
        return rouge_w_12_f_score_cb;
    }

    public void setRouge_w_12_f_score_cb(double rouge_w_12_f_score_cb) {
        this.rouge_w_12_f_score_cb = rouge_w_12_f_score_cb;
    }

    public double getRouge_w_12_f_score_ce() {
        return rouge_w_12_f_score_ce;
    }

    public void setRouge_w_12_f_score_ce(double rouge_w_12_f_score_ce) {
        this.rouge_w_12_f_score_ce = rouge_w_12_f_score_ce;
    }

    public double getRouge_s_star_recall() {
        return rouge_s_star_recall;
    }

    public void setRouge_s_star_recall(double rouge_s_star_recall) {
        this.rouge_s_star_recall = rouge_s_star_recall;
    }

    public double getRouge_s_star_recall_cb() {
        return rouge_s_star_recall_cb;
    }

    public void setRouge_s_star_recall_cb(double rouge_s_star_recall_cb) {
        this.rouge_s_star_recall_cb = rouge_s_star_recall_cb;
    }

    public double getRouge_s_star_recall_ce() {
        return rouge_s_star_recall_ce;
    }

    public void setRouge_s_star_recall_ce(double rouge_s_star_recall_ce) {
        this.rouge_s_star_recall_ce = rouge_s_star_recall_ce;
    }

    public double getRouge_s_star_precision() {
        return rouge_s_star_precision;
    }

    public void setRouge_s_star_precision(double rouge_s_star_precision) {
        this.rouge_s_star_precision = rouge_s_star_precision;
    }

    public double getRouge_s_star_precision_cb() {
        return rouge_s_star_precision_cb;
    }

    public void setRouge_s_star_precision_cb(double rouge_s_star_precision_cb) {
        this.rouge_s_star_precision_cb = rouge_s_star_precision_cb;
    }

    public double getRouge_s_star_precision_ce() {
        return rouge_s_star_precision_ce;
    }

    public void setRouge_s_star_precision_ce(double rouge_s_star_precision_ce) {
        this.rouge_s_star_precision_ce = rouge_s_star_precision_ce;
    }

    public double getRouge_s_star_f_score() {
        return rouge_s_star_f_score;
    }

    public void setRouge_s_star_f_score(double rouge_s_star_f_score) {
        this.rouge_s_star_f_score = rouge_s_star_f_score;
    }

    public double getRouge_s_star_f_score_cb() {
        return rouge_s_star_f_score_cb;
    }

    public void setRouge_s_star_f_score_cb(double rouge_s_star_f_score_cb) {
        this.rouge_s_star_f_score_cb = rouge_s_star_f_score_cb;
    }

    public double getRouge_s_star_f_score_ce() {
        return rouge_s_star_f_score_ce;
    }

    public void setRouge_s_star_f_score_ce(double rouge_s_star_f_score_ce) {
        this.rouge_s_star_f_score_ce = rouge_s_star_f_score_ce;
    }

    public double getRouge_su_star_recall() {
        return rouge_su_star_recall;
    }

    public void setRouge_su_star_recall(double rouge_su_star_recall) {
        this.rouge_su_star_recall = rouge_su_star_recall;
    }

    public double getRouge_su_star_recall_cb() {
        return rouge_su_star_recall_cb;
    }

    public void setRouge_su_star_recall_cb(double rouge_su_star_recall_cb) {
        this.rouge_su_star_recall_cb = rouge_su_star_recall_cb;
    }

    public double getRouge_su_star_recall_ce() {
        return rouge_su_star_recall_ce;
    }

    public void setRouge_su_star_recall_ce(double rouge_su_star_recall_ce) {
        this.rouge_su_star_recall_ce = rouge_su_star_recall_ce;
    }

    public double getRouge_su_star_precision() {
        return rouge_su_star_precision;
    }

    public void setRouge_su_star_precision(double rouge_su_star_precision) {
        this.rouge_su_star_precision = rouge_su_star_precision;
    }

    public double getRouge_su_star_precision_cb() {
        return rouge_su_star_precision_cb;
    }

    public void setRouge_su_star_precision_cb(double rouge_su_star_precision_cb) {
        this.rouge_su_star_precision_cb = rouge_su_star_precision_cb;
    }

    public double getRouge_su_star_precision_ce() {
        return rouge_su_star_precision_ce;
    }

    public void setRouge_su_star_precision_ce(double rouge_su_star_precision_ce) {
        this.rouge_su_star_precision_ce = rouge_su_star_precision_ce;
    }

    public double getRouge_su_star_f_score() {
        return rouge_su_star_f_score;
    }

    public void setRouge_su_star_f_score(double rouge_su_star_f_score) {
        this.rouge_su_star_f_score = rouge_su_star_f_score;
    }

    public double getRouge_su_star_f_score_cb() {
        return rouge_su_star_f_score_cb;
    }

    public void setRouge_su_star_f_score_cb(double rouge_su_star_f_score_cb) {
        this.rouge_su_star_f_score_cb = rouge_su_star_f_score_cb;
    }

    public double getRouge_su_star_f_score_ce() {
        return rouge_su_star_f_score_ce;
    }

    public void setRouge_su_star_f_score_ce(double rouge_su_star_f_score_ce) {
        this.rouge_su_star_f_score_ce = rouge_su_star_f_score_ce;
    }
}
