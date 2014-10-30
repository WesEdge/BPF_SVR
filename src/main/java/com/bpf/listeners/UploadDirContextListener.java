package com.bpf.listeners;

import com.bpf.BPF;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class UploadDirContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent)  {

        ServletContext ctx = servletContextEvent.getServletContext();

        String fileStorageType = BPF.setContextAttributeFromWebConfig(ctx, BPF.ConfigKeys.FILE_STORAGE_TYPE.name());

        if (fileStorageType.equals(BPF.FileStorageTypes.LOCAL_FILE_DIR.name())){
            // local file directory path will be used to store files

            String fileDirPath = BPF.setContextAttributeFromWebConfig(ctx, BPF.ConfigKeys.FILE_DIR_PATH.name());

            // create the directory if it does not exist
            File fileDir = new File(fileDirPath);
            if(!fileDir.exists()) fileDir.mkdirs();

            // save the directory to context
            BPF.setContextAttribute(ctx, BPF.ConfigKeys.FILE_DIR.name(), fileDir);

        }
        else if (fileStorageType.equals(BPF.FileStorageTypes.S3.name())){
            // S3 will be used to store files
            ctx.setAttribute("test", "test");

        }




    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

}