package com.bpf;

import javax.servlet.ServletContext;

/**
 * Created by wedge on 10/29/14.
 */
public class BPF {

    public static enum ConfigKeys {
        FILE_STORAGE_TYPE,
        FILE_DIR,
        FILE_DIR_PATH,
        MONGO_HOST,
        MONGO_PORT,
        MONGO_CLIENT
    }

    public static enum FileStorageTypes {
        S3,
        LOCAL_FILE_DIR
    }

    public static String setContextAttributeFromWebConfig(ServletContext ctx, String configKey){

        String configValue = ctx.getInitParameter(configKey);    // get the value from web config
        BPF.setContextAttribute(ctx, configKey, configValue);   // sets the context param
        return configValue;

    }

    public static void setContextAttribute(ServletContext ctx, String key, Object value){
        ctx.setAttribute(key, value); // sets the context param
        System.out.println(String.format("%s = %s", key, value.toString()));   // write "key = value" to console
    }

}
