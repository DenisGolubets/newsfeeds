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
 * Dao layer for News Feed
 */

@Service
public class NewsDao {

    protected static Logger logger = Logger.getLogger(News.class);
    private SessionFactory sessionFactory;

    public NewsDao() {
        this.sessionFactory = HibernateSessionFactory.getSessionFactory();
    }


    /**
     * return all List<News>
     */
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

    /**
     * return sorted desc List<News> by given page number
     * and quantity of records per page
     */
    public List<News> getNewsListByPage(int page, int totalRecordsPerPage) {
        Session session = sessionFactory.openSession();
        List<News> list = null;
        try {
            Query query = session.createQuery("From News order by id desc ");
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


    /**
     * persist news
     */
    public void persist(News news) {
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();
            session.persist(news);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            logger.error("", e);
        } finally {
            session.close();
        }
    }

    /**
     * return quantity of news in table
     */
    public int getRecordsQuantity() {
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

    /**
     * delete news
     */

    public void delete(News news){
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(news);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            logger.error("", e);
        } finally {
            session.close();
        }
    }
}
