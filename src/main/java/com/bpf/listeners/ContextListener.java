package com.bpf.listeners;

import com.bpf.BPF;
import com.mongodb.MongoClient;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent)  {

        ServletContext ctx = servletContextEvent.getServletContext();
        BPF.setInitVars(ctx);
        BPF.getFileStore().setInitVars();
        BPF.getMongo().setInitVars();

    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        BPF.getMongo().close();
    }

}