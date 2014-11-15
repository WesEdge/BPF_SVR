package com.bpf;

import com.mongodb.MongoClient;

import javax.servlet.ServletContext;

/**
 * Created by wedge on 10/29/14.
 */

public class BPF {

    private static ServletContext context = null;

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

    public static boolean isS3(){
        boolean s3 = BPF.getContext().getInitParameter(BPF.ConfigKeys.FILE_STORAGE_TYPE.name()).equals(FileStorageTypes.S3.name());
        return s3;
    }

    public static ServletContext getContext(){
        return BPF.context;
    }

    public static void setInitVars(ServletContext context){
        BPF.context = context;
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

    public static MongoClient getMongoClient(){
        return (MongoClient) BPF.getContext().getAttribute("MONGO_CLIENT");
    }

    public static String getFormattedSize(long bytes){

        if (bytes < 1024){
            return String.format("%s B", String.valueOf(bytes));
        }

        long kb = bytes/1024;

        if (kb < 1024){
            return String.format("%s K", String.valueOf(kb));
        }

        long mb = kb/1024;

        if (mb < 1024){
            return String.format("%s M", String.valueOf(mb));
        }

        long gb = mb/1024;

        return String.format("%s G", String.valueOf(gb));

    }


}
