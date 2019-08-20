package de.bigabig.wikihowqa.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class DatePoint {

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Berlin")
    private Date t;
    private double y;

    public DatePoint() {
    }

    public DatePoint(Date t, double y) {
        this.t = t;
        this.y = y;
    }

    public Date getT() {
        return t;
    }

    public void setT(Date t) {
        this.t = t;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
