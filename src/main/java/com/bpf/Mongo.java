package com.bpf;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import org.apache.commons.fileupload.FileItem;
import org.bson.types.ObjectId;
import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * Created by wedge on 11/15/14.
 */
public class Mongo {

    private String host = null;
    private int port;
    private MongoClient client = null;

    public void setInitVars(){

        ///--------------------------------------
        // open & save mongodb client connection
        ///--------------------------------------

        try {

            host = BPF.getConfigParam(BPF.ConfigKeys.MONGO_HOST.name());
            port = Integer.parseInt(BPF.getConfigParam(BPF.ConfigKeys.MONGO_PORT.name()));
            client = new MongoClient(host, port);

            System.out.println("MongoClient initialized successfully");

        } catch (java.net.UnknownHostException e) {
            throw new RuntimeException("MongoClient init failed");
        }

    }

    //saves the upload txn to mongo
    public ObjectId saveUploadTxn(FileItem fileItem, HttpServletRequest request) throws UnknownHostException {

        ObjectId id = null;

        DB db = client.getDB( "bpf" );
        DBCollection coll = db.getCollection("uploads");

        BasicDBObject doc = new BasicDBObject("name", fileItem.getName())
                .append("type", fileItem.getContentType())
                .append("bytes", fileItem.getSize())
                .append("size", BPF.getFormattedSize(fileItem.getSize()))
                .append("date", new Date())
                .append("tags", Tags.getTags(request, fileItem));

        coll.insert(doc);

        // index for keyword searching -> http://docs.mongodb.org/manual/tutorial/model-data-for-keyword-search/
        coll.createIndex(new BasicDBObject("tags", 1));

        id = (ObjectId)doc.get( "_id" );

        return id;

    }

    public void close(){

        if (null != client){
            client.close();
            System.out.println("MongoClient closed successfully");
        }

    }

    public static MongoClient getClient(){
        return BPF.getMongo().client;
    }

    public static DB getDB(String dbName){
        return getClient().getDB(dbName);
    }

}
