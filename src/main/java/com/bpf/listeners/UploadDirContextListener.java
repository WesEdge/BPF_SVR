package com.bpf.listeners;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class UploadDirContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent)  {

        ///--------------------------------------
        //set the server file upload directory
        ///--------------------------------------

        ServletContext ctx = servletContextEvent.getServletContext();
        String path = ctx.getInitParameter("FILE_DIR");
        File file = new File(path);
        if(!file.exists()) file.mkdirs();
        System.out.println("File Directory exists to be used for storing files.. " + path);
        ctx.setAttribute("FILES_DIR_FILE", file);
        ctx.setAttribute("FILES_DIR", path);

    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

}