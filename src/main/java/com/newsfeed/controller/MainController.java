package com.newsfeed.controller;

import com.newsfeed.domain.News;
import com.newsfeed.dao.NewsDao;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by golubets on 19.04.2017.
 */
@Controller
public class MainController {

    protected static Logger logger = Logger.getLogger(News.class);

    @Autowired
    private NewsDao newsDao;
    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        int totalRecordsPerPage = 10;
        int pages = (int) Math.ceil((double) newsDao.getCountRecords() / totalRecordsPerPage);
        System.out.println(pages);
        List<News> list;
        list = newsDao.getByPage(1, totalRecordsPerPage);
        modelAndView.addObject("newsList", list);
        modelAndView.addObject("pages", pages);
        return modelAndView;
    }

    @RequestMapping(value="/{pageid}")
    public ModelAndView edit(@PathVariable int pageid){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        int totalRecordPerPage = 10;
        int pages = (int) Math.ceil((double)newsDao.getCountRecords() / totalRecordPerPage);
        List<News> list;
        list = newsDao.getByPage(pageid, totalRecordPerPage);
        modelAndView.addObject("newsList", list);
        modelAndView.addObject("pages", pages);
        return modelAndView;
    }

    @RequestMapping(value = "addnews")
    public ModelAndView news() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("newsAdd");
        modelAndView.addObject("news", new News());
        return modelAndView;
    }

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

            entity.setNewsDate(new java.sql.Timestamp(new Date().getTime()).toString());
            entity.setPicturePath(path);
            newsDao.persist(entity);
        } catch (IOException e) {
            logger.error("", e);
        }

        if (result.hasErrors()) {
            return "redirect:/";
        }

        return "redirect:/";

    }
}
