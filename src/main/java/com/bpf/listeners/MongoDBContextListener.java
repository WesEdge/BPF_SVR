package com.bpf.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import com.mongodb.MongoClient;

@WebListener
public class MongoDBContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent)  {

        ServletContext ctx = servletContextEvent.getServletContext();

        ///--------------------------------------
        // open & save mongodb client connection
        ///--------------------------------------

        try {

            MongoClient mongo = new MongoClient(
                    ctx.getInitParameter("MONGODB_HOST"),
                    Integer.parseInt(ctx.getInitParameter("MONGODB_PORT")));

            System.out.println("MongoClient initialized successfully");

            ctx.setAttribute("MONGO_CLIENT", mongo);

        } catch (java.net.UnknownHostException e) {
            throw new RuntimeException("MongoClient init failed");
        }

    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

        ///--------------------------------
        // close mongodb client connection
        ///--------------------------------
        MongoClient mongo = (MongoClient) servletContextEvent.getServletContext().getAttribute("MONGO_CLIENT");
        mongo.close();
        System.out.println("MongoClient closed successfully");

    }

}