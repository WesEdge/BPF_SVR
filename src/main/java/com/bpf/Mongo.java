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

    //saves the upload txn to mongo
    public static ObjectId save(FileItem fileItem, HttpServletRequest request) throws UnknownHostException {

        MongoClient mongoClient = null;
        ObjectId id = null;

        mongoClient = BPF.getMongoClient();

        DB db = mongoClient.getDB( "bpf" );
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

}
