package com.newsfeed.controller;

import com.newsfeed.dao.NewsDao;
import com.newsfeed.domain.News;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Servlet controller
 */
@Controller
public class MainController {

    protected static Logger logger = Logger.getLogger(News.class);

    @Autowired
    private NewsDao newsDao;
    @Autowired
    ServletContext servletContext;

    /**
     * Request mapping index page
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        int totalRecordsPerPage = 10;
        int pages = (int) Math.ceil((double) newsDao.getRecordsQuantity() / totalRecordsPerPage);
        List<News> list;
        list = newsDao.getNewsListByPage(1, totalRecordsPerPage);
        modelAndView.addObject("newsList", list);
        modelAndView.addObject("pages", pages);
        return modelAndView;
    }
    /**
     * Request mapping appointed quantity of records in page
     */
    @RequestMapping(value="/{pageid}")
    public ModelAndView edit(@PathVariable int pageid){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        int totalRecordPerPage = 10;
        int pages = (int) Math.ceil((double)newsDao.getRecordsQuantity() / totalRecordPerPage);
        List<News> list;
        list = newsDao.getNewsListByPage(pageid, totalRecordPerPage);
        modelAndView.addObject("newsList", list);
        modelAndView.addObject("pages", pages);
        return modelAndView;
    }

    /**
     * Request mapping add news form
     */
    @RequestMapping(value = "addnews")
    public ModelAndView news() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("addnews");
        modelAndView.addObject("news", new News());
        return modelAndView;
    }

    /**
     * Validate news date, image and return back if find errors
     * or if validate successfully redirect to news feed(index page)
     */
    @RequestMapping(value = "addnews", method = RequestMethod.POST)
    public String saveNews(@RequestParam("file") MultipartFile file, @Valid News entity, BindingResult result) {

        try {
            String path = null;
            if (file.getSize() > 0) {
                String fileType = "." + file.getContentType().substring(file.getContentType().indexOf("/") + 1);
                path = "/resources/images/" + new Date().getTime() + fileType;
                File dest = new File(servletContext.getRealPath("/") + path);
                FileUtils.copyInputStreamToFile(file.getInputStream(), dest);
            }
            if (result.hasErrors()){
                return "addnews";
            }

            entity.setNewsDate(new java.sql.Timestamp(new Date().getTime()).toString());
            entity.setPicturePath(path);
            newsDao.persist(entity);
        } catch (IOException e) {
            logger.error("", e);
        }

        return "redirect:/";

    }
}
