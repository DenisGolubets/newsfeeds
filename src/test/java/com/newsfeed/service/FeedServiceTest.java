package com.newsfeed.service;

import com.newsfeed.domain.News;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by golubets on 18.04.2017.
 */
public class FeedServiceTest {

    @Test
    public void getAll() throws Exception {
        NewsService feedService = new NewsService();

        Assert.assertTrue(feedService.getAll().size()==0);
    }

    @Test
    public void persist() throws Exception {
        NewsService feedService = new NewsService();
        News feed = new News();
        feed.setNewsDate(new Timestamp(new Date().getTime()).toString());
        feed.setTitle("Title");
        feed.setMessage("message");
        feed.setPicturePath("D:\\000000000000000002.png");
        feedService.persist(feed);
        News feed1 = feedService.getAll().get(0);
        Assert.assertTrue(feed.equals(feed1));
    }
}