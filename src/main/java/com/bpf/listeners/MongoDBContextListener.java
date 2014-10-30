package com.bpf.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.bpf.BPF;
import com.mongodb.MongoClient;

@WebListener
public class MongoDBContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent)  {

        ServletContext ctx = servletContextEvent.getServletContext();

        ///--------------------------------------
        // open & save mongodb client connection
        ///--------------------------------------

        try {

            String mongoHost = BPF.setContextAttributeFromWebConfig(ctx, BPF.ConfigKeys.MONGO_HOST.name());
            String mongoPort = BPF.setContextAttributeFromWebConfig(ctx, BPF.ConfigKeys.MONGO_PORT.name());

            MongoClient mongo = new MongoClient(mongoHost, Integer.parseInt(mongoPort));

            System.out.println("MongoClient initialized successfully");

            BPF.setContextAttribute(ctx, BPF.ConfigKeys.MONGO_CLIENT.name(), mongo);

        } catch (java.net.UnknownHostException e) {
            throw new RuntimeException("MongoClient init failed");
        }

    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

        ///--------------------------------
        // close mongodb client connection
        ///--------------------------------
        MongoClient mongo = (MongoClient) servletContextEvent.getServletContext().getAttribute(BPF.ConfigKeys.MONGO_CLIENT.name());
        mongo.close();
        System.out.println("MongoClient closed successfully");

    }

}