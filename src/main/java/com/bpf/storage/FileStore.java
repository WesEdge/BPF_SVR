package com.bpf.storage;

import org.apache.commons.fileupload.FileItem;
import org.bson.types.ObjectId;

public interface FileStore {

    public String getType();
    public void setInitVars();
    public void save(FileItem fileItem, ObjectId id) throws Exception;

}
