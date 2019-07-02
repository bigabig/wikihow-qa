package de.bigabig.wikihowqa.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RatingsOverTimeResponse {

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Berlin")
    private List<Date> labels = new ArrayList<Date>();
    private List<DatePoint> data = new ArrayList<DatePoint>();

    public RatingsOverTimeResponse() {
    }

    public RatingsOverTimeResponse(List<Date> labels, List<DatePoint> data) {
        this.labels = labels;
        this.data = data;
    }

    public List<Date> getLabels() {
        return labels;
    }

    public void setLabels(List<Date> labels) {
        this.labels = labels;
    }

    public List<DatePoint> getData() {
        return data;
    }

    public void setData(List<DatePoint> data) {
        this.data = data;
    }
}
