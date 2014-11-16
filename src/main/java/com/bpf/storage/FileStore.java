package com.bpf.storage;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.bson.types.ObjectId;
import javax.servlet.ServletContext;

public interface FileStore {

    public String getType();
    public void setInitVars();
    public void save(FileItem fileItem, ObjectId id) throws FileUploadException, Exception;

}
