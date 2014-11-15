package com.bpf.file;

import com.bpf.BPF;
import org.apache.commons.fileupload.FileUploadException;
import org.bson.types.ObjectId;
import org.apache.commons.fileupload.FileItem;

import java.io.File;

/**
 * Created by wedge on 11/15/14.
 */
public class LocalDisk {

    public static void save(FileItem fileItem, ObjectId id) throws FileUploadException, Exception{

        // declare the file to save
        File file = new File(BPF.getContext().getAttribute(BPF.ConfigKeys.FILE_DIR_PATH.name())
                + File.separator
                //+ fileItem.getName());
                + id.toString());

        System.out.println("Absolute Path at server="+file.getAbsolutePath());

        // save the file to disk
        fileItem.write(file);

    }

}
