package com.bpf.storage;

import com.bpf.BPF;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.bson.types.ObjectId;

import javax.servlet.ServletContext;

/**
 * Created by wedge on 11/15/14.
 */
public class S3 implements FileStore {

    private String accessKey = null;
    private String secretKey = null;
    private String bucketName = null;
    private String type = null;

    public String getType(){
        return this.type;
    }

    public void setInitVars(){

        type = BPF.FileStorageTypes.S3.name();

        accessKey = BPF.getConfigParam(BPF.ConfigKeys.S3_ACCESS_KEY.name());
        secretKey = BPF.getConfigParam(BPF.ConfigKeys.S3_SECRET_KEY.name());
        bucketName = BPF.getConfigParam(BPF.ConfigKeys.S3_BUCKET_NAME.name());
    }

    public void save(FileItem fileItem, ObjectId id) throws FileUploadException, Exception{





    }



}
