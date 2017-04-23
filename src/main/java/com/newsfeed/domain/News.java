package com.newsfeed.domain;

import com.sun.istack.internal.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by golubets on 18.04.2017.
 */
@Entity
@Table(name = "feed", schema = "news_feed")
public class News implements Serializable {
    private int id;
    private String title;
    private String newsDate;
    private String message;
    private String picturePath;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 65)
    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Basic
    @Column(name = "news_date")
    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(String feedDate) {
        this.newsDate = feedDate;
    }

    @NotNull
    @NotEmpty
    @Size(min = 100, max = 5000)
    @Basic
    @Column(name = "message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Basic
    @Column(name = "picture_path")
    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        News that = (News) o;

        if (id != that.id) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (newsDate != null ? !newsDate.equals(that.newsDate) : that.newsDate != null) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        if (picturePath != null ? !picturePath.equals(that.picturePath) : that.picturePath != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (newsDate != null ? newsDate.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (picturePath != null ? picturePath.hashCode() : 0);
        return result;
    }
}
