package com.newsfeed.dao;

import com.newsfeed.domain.News;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.AssertTrue;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by golubets on 18.04.2017.
 */
@Service
public class FeedServiceTest {

    @Before
    public void before() {
        NewsDao newsDao = new NewsDao();
        List<News> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            News news = new News();
            news.setTitle("Title");
            news.setMessage("message");
            news.setPicturePath("D:\\000000000000000002.png");
            news.setNewsDate(new Timestamp(new Date().getTime()).toString());
            newsDao.persist(news);
        }
    }

    @Test
    public void getAll() throws Exception {
        NewsDao newsDao = new NewsDao();
        int countRows = newsDao.getRecordsQuantity();
        Assert.assertTrue(newsDao.getAll().size() == countRows);
    }

    @Test
    public void persist() throws Exception {
        NewsDao newsDao = new NewsDao();
        int countRows = newsDao.getRecordsQuantity();

        News news = new News();
        news.setTitle("Title");
        news.setMessage("message");
        news.setPicturePath("D:\\000000000000000002.png");
        news.setNewsDate(new Timestamp(new Date().getTime()).toString());
        newsDao.persist(news);

        Assert.assertTrue(newsDao.getRecordsQuantity()==countRows+1);

        newsDao.delete(newsDao.getNewsListByPage(1,10).get(0));
    }

    @Test
    public void getByPage() throws Exception {
        NewsDao newsDao = new NewsDao();
        List<News> list = newsDao.getNewsListByPage(1,10);
        Assert.assertTrue(list.size()==10);
    }

    @Test
    public void getCount() throws Exception {
        NewsDao newsDao = new NewsDao();
        Assert.assertTrue(newsDao.getRecordsQuantity()>0);
    }

    @Test
    public void delete(){
        NewsDao newsDao = new NewsDao();
        int countRows = newsDao.getRecordsQuantity();
        News news = new News();
        news.setTitle("Delete");
        news.setMessage("message");
        news.setPicturePath("D:\\000000000000000002.png");
        news.setNewsDate(new Timestamp(new Date().getTime()).toString());
        newsDao.persist(news);

        newsDao.delete(newsDao.getNewsListByPage(1,10).get(0));

        Assert.assertTrue(countRows==newsDao.getRecordsQuantity());

    }

    @After
    public void after(){
        NewsDao newsDao = new NewsDao();
        for (News news: newsDao.getNewsListByPage(1,10)){
            newsDao.delete(news);
        }
    }
}