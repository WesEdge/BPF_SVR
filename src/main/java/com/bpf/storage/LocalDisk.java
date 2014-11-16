package com.bpf.storage;

import com.bpf.BPF;
import org.bson.types.ObjectId;
import org.apache.commons.fileupload.FileItem;
import java.io.File;

public class LocalDisk implements FileStore {

    private String fileDirPath = null;
    private File fileDir = null;
    private String type = null;

    public String getType(){
        return this.type;
    }

    public void setInitVars(){

        type = BPF.FileStorageTypes.LOCAL_DISK.name();

        fileDirPath = BPF.getConfigParam(BPF.ConfigKeys.FILE_DIR_PATH.name());

        // create the directory if it does not exist
        fileDir = new File(fileDirPath);
        if(!fileDir.exists()) fileDir.mkdirs();

    }

    public void save(FileItem fileItem, ObjectId id) throws Exception{

        String filePath = String.format("%s%s%s", fileDirPath, File.separator, id.toString());

        // declare the file to save
        File file = new File(filePath);

        // save the file to disk
        fileItem.write(file);

    }

}
