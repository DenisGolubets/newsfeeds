package com.newsfeed.service;

import com.newsfeed.domain.News;
import com.newsfeed.util.HibernateSessionFactory;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for processing News Feed
 */

@Service
public class NewsService {

    protected static Logger logger = Logger.getLogger(News.class);
    private SessionFactory sessionFactory;

    public NewsService() {
        this.sessionFactory = HibernateSessionFactory.getSessionFactory();
    }

    public List<News> getAll() {
        Session session = sessionFactory.openSession();
        List<News> list = null;
        try {
            session.beginTransaction();
            list = session.createQuery("From News ").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            logger.error("", e);
        }
        return list;
    }

    public void persist(News feed) {
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();
            session.persist(feed);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            logger.error("", e);
        }
    }
}
