package com.newsfeed.dao;

import com.newsfeed.domain.News;
import com.newsfeed.util.HibernateSessionFactory;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for processing News Feed
 */

@Service
public class NewsDao {

    protected static Logger logger = Logger.getLogger(News.class);
    private SessionFactory sessionFactory;

    public NewsDao() {
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
        } finally {
            session.close();
        }
        return list;
    }

    public List<News> getByPage(int page, int totalRecordsPerPage) {
        Session session = sessionFactory.openSession();
        List<News> list = null;
        try {
            Query query = session.createQuery("From News");
            query.setFirstResult((page - 1) * totalRecordsPerPage);
            query.setMaxResults(totalRecordsPerPage);
            list = query.list();
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            session.close();
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
        } finally {
            session.close();
        }
    }

    public int getCountRecords() {
        Session session = sessionFactory.openSession();

        int count = 0;
        try {
            Number quantityRowsInDb = (Number) (session.createQuery("select count(*) from News ").uniqueResult());
            count = quantityRowsInDb.intValue();
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            session.close();
        }
        return count;
    }
}
