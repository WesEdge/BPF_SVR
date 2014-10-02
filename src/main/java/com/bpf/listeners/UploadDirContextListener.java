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
        String rootPath = System.getProperty("catalina.home");
        String relativePath = ctx.getInitParameter("UPLOAD_FILE_DIR");
        File file = new File(rootPath + File.separator + relativePath);
        if(!file.exists()) file.mkdirs();
        System.out.println("File Directory created to be used for storing files");
        ctx.setAttribute("FILES_DIR_FILE", file);
        ctx.setAttribute("FILES_DIR", rootPath + File.separator + relativePath);

    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

}