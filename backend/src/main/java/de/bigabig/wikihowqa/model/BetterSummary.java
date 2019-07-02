package de.bigabig.wikihowqa.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "summaries")
public class BetterSummary {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="title",nullable = false)
    private String title;

    @Column(name="method",nullable = false)
    private String method;

    @Column(name="summary",nullable = false, length = 512)
    private String summary;

    @Column(name="timestamp",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Berlin")
    private Date timestamp;

    public BetterSummary() {
    }

    public BetterSummary(String title, String method, String summary, Date timestamp) {
        this.title = title;
        this.method = method;
        this.summary = summary;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
