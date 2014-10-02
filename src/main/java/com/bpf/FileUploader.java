package com.bpf;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import org.bson.types.ObjectId;

public class FileUploader {

    private HttpServletRequest request = null;
    private ServletFileUpload uploader = null;
    private FileItem fileItem = null;

    public FileUploader(HttpServletRequest request, ServletFileUpload uploader) throws FileUploadException, Exception {

        this.request = request;
        this.uploader = uploader;

        //this.saveToDB();
        this.saveFileToDisk();

    }

    //saves the upload txn to mongo
    private ObjectId saveToDB(FileItem fileItem) throws UnknownHostException {

        MongoClient mongoClient = null;
        ObjectId id = null;

        mongoClient = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
        DB db = mongoClient.getDB( "bpf" );
        DBCollection coll = db.getCollection("uploads");

        BasicDBObject doc = new BasicDBObject("name", fileItem.getName())
                .append("type", fileItem.getContentType())
                .append("bytes", fileItem.getSize())
                .append("size", this.getFormattedSize(fileItem.getSize()))
                .append("date", new Date())
                .append("tags", Tags.getTags(request, fileItem));

        coll.insert(doc);

        // index for keyword searching -> http://docs.mongodb.org/manual/tutorial/model-data-for-keyword-search/
        coll.createIndex(new BasicDBObject("tags", 1));

        id = (ObjectId)doc.get( "_id" );

        return id;

    }

    private String getFormattedSize(long bytes){


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

    private void saveFileToDisk() throws FileUploadException, Exception{

        List<FileItem> fileItemsList = uploader.parseRequest(request);
        Iterator<FileItem> fileItemsIterator = fileItemsList.iterator();

        while(fileItemsIterator.hasNext()){

            fileItem = fileItemsIterator.next();

            //saves the upload txn to mongodb
            ObjectId id = saveToDB(fileItem);

            // declare the file to save
            File file = new File(request.getServletContext().getAttribute("FILES_DIR")
                    + File.separator
                    //+ fileItem.getName());
                    + id.toString());

            System.out.println("Absolute Path at server="+file.getAbsolutePath());

            // save the file to disk
            fileItem.write(file);

        }

    }

    public FileItem getFileItem(){
        return fileItem;
    }

}
