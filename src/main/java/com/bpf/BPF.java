package com.bpf;

import com.bpf.storage.LocalDisk;
import com.bpf.storage.S3;
import com.bpf.storage.FileStore;

import javax.servlet.ServletContext;

/**
 * Created by wedge on 10/29/14.
 */

public class BPF {

    private static ServletContext context = null;
    private static FileStore fileStore = null;
    private static Mongo mongo = null;

    public static enum ConfigKeys {
        FILE_STORAGE_TYPE,
        FILE_DIR_PATH,
        MONGO_HOST,
        MONGO_PORT,
        S3_ACCESS_KEY,
        S3_SECRET_KEY,
        S3_BUCKET_NAME
    }

    public static enum FileStorageTypes {
        S3,
        LOCAL_DISK
    }

    public static ServletContext getContext(){
        return BPF.context;
    }

    public static void setInitVars(ServletContext context){
        BPF.context = context;
        setFileStore();
        setMongo();
    }

    private static void setMongo(){
        mongo = new Mongo();
    }

    public static Mongo getMongo(){
        return mongo;
    }

    public static FileStore getFileStore(){
        return fileStore;
    }

    private static void setFileStore(){

        String fileStorageType = BPF.getConfigParam(BPF.ConfigKeys.FILE_STORAGE_TYPE.name());

        if (fileStorageType.equals(BPF.FileStorageTypes.S3.name())){
            fileStore = new S3();
        }
        else{
            fileStore = new LocalDisk();
        }

    }

    public static String getConfigParam(String configKey){
        String configValue = BPF.getContext().getInitParameter(configKey);
        writeAttribute(configKey, configValue);
        return configValue;
    }

    public static void writeAttribute(String key, Object value){
        System.out.println(String.format("%s = %s", key, value.toString()));   // write "key = value" to console
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
