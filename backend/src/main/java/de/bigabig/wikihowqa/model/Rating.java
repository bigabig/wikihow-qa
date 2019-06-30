package de.bigabig.wikihowqa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ratings")
public class Rating {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="word",nullable = false)
    private String title;

    @Column(name="method",nullable = false)
    private String method;

    @Column(name="rating",nullable = false)
    private int rating;

    @Column(name="timestamp",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    public Rating() {
    }

    public Rating(String title, String method, int rating, Date timestamp) {
        this.title = title;
        this.method = method;
        this.rating = rating;
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

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
