package com.bpf.upload;

import com.bpf.BPF;
import com.bpf.file.LocalDisk;
import com.bpf.file.S3;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;

import org.bson.types.ObjectId;

public class FileUploader {

    private HttpServletRequest request = null;
    private ServletFileUpload uploader = null;
    private FileItem fileItem = null;
    private ServletContext ctx = null;

    public FileUploader(HttpServletRequest request, ServletFileUpload uploader) throws FileUploadException, Exception {

        this.request = request;
        this.uploader = uploader;
        this.ctx = request.getSession().getServletContext();

        List<FileItem> fileItemsList = uploader.parseRequest(request);
        Iterator<FileItem> fileItemsIterator = fileItemsList.iterator();

        while(fileItemsIterator.hasNext()){

            fileItem = fileItemsIterator.next();

            ObjectId id = com.bpf.Mongo.save(fileItem, request); // saves the upload transaction to mongo db

            if (BPF.isS3()){
                S3.save(fileItem, id);  //saves file to S3
            }
            else{
                LocalDisk.save(fileItem, id);   // saves file to local disk
            }


        }

    }

    public FileItem getFileItem(){
        return fileItem;
    }

}
