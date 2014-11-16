package com.bpf.upload;

import com.bpf.BPF;
import com.bpf.storage.LocalDisk;
import com.bpf.storage.S3;
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
            ObjectId id = BPF.getMongo().saveUploadTxn(fileItem, request); // saves the upload transaction to mongo db
            BPF.getFileStore().save(fileItem, id);

        }

    }

    public FileItem getFileItem(){
        return fileItem;
    }

}
