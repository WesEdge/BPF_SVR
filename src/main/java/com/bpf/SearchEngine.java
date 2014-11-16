package com.bpf;

import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBCollection;
import com.mongodb.QueryBuilder;
import javax.servlet.http.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class SearchEngine {

    private DBCollection dbCollection = null;
    private String LATEST = "[latest]";
    private String POPULAR = "[popular]";
    private int MAX_RETURN_LIMIT = 100;

    public SearchEngine(HttpServletRequest request){

        DB db = Mongo.getDB( "bpf" );
        dbCollection = db.getCollection("uploads");

    }

    public String search(HttpServletRequest request){

        String query = request.getParameter("query");
        if (query.equals("")){ query = LATEST; }

        return this.search(query);

    }

    public String search(String query){

        if (query.equals(LATEST)){
            return getLatest();
        } else if (query.equals(POPULAR)){
            return getPopular();
        } else{
            ArrayList<String> tags = getTags(query);
            return getByTag(tags);
        }

    }

    public ArrayList<String> getTags(String query){
        String tag = query;
        tag = tag.replace("#"," ");
        tag = tag.replace(" ", ",");
        tag = tag.trim();
        String delimiter = ",";
        ArrayList<String> tags = Tags.getParts(tag, delimiter, null);
        return tags;
    }

    public String getLatest(){
        BasicDBObject sort = new BasicDBObject( "_id" , -1 );
        DBCursor cursor = dbCollection.find().limit(MAX_RETURN_LIMIT).sort(sort);
        String json = Json.getJson(cursor);
        return json;
    }

    public String getPopular(){
        return getDebugJson();
    }

    public String getByTag(ArrayList<String> tags){

        DBObject sort = new BasicDBObject( "_id" , -1 );
        DBObject where = new BasicDBObject();
        DBCursor cursor = null;

        //----------------------------
        // regex find where tags like '%tag%'
        //----------------------------
        //Pattern regex = getTagRegex(tag);
        //where.put("tags", regex);
        //cursor = dbCollection.find(where).limit(MAX_RETURN_LIMIT).sort(sort);
        //----------------------------

        //----------------------------
        // find exact tag equality
        //----------------------------
        QueryBuilder builder = QueryBuilder.start("tags").all(tags);
        where = builder.get();
        cursor = dbCollection.find(where).limit(10).sort(sort);
        //----------------------------

        String json = Json.getJson(cursor);
        return json;
    }

    private Pattern getTagRegex(String tag){
        // if tag contains splitters then we need to create a more complex "and" regex

        String delimiter = ",";
        String temp = tag.replace(" ", ",");
        String[] tags = temp.split(delimiter);

        if (tags.length > 1){
            String regex = "";

            // TODO: "and" regex


            return Pattern.compile(regex);
        }

        return Pattern.compile(tag);
    }

    public String getDebugJson(){

        return "[" +
                "{\"_id\":\"12344\",\"name\":\"The.True.Story.of.Wrestlemania.x264-WD\",\"size\":\"1.5 G\",\"type\":\"video/mp4\", \"date\":\"12/12/2013\",\"tags\":[\"wrestlemania 3\",\"big john stud\",\"wrestling\",\"wwf\"]}," +
                "{\"_id\":\"23456\",\"name\":\"Greys.Anatomy.S01E12.720p.HDTV.X264-DIMENSION\",\"size\":\"123 M\",\"type\":\"video/x-msvideo\",\"date\":\"12/14/2013\",\"tags\":[\"grays anatomy\",\"se01\",\"ep12\"]}," +
                "{\"_id\":\"34567\",\"name\":\"Seether Discography\",\"size\":\"250 M\",\"type\":\"audio/mp3\",\"date\":\"2/16/2014\",\"tags\":[\"seether\",\"discography\"]}," +
                "{\"_id\":\"45673\",\"name\":\"Call of Duty Black Ops 2.iso\",\"size\":\"54.3 M\",\"type\":\"application/octet-stream\",\"date\":\"11/23/2013\",\"tags\":[\"call of duty\",\"black ops\",\"video game\",\"ps4\"]}," +
                "{\"_id\":\"54321\",\"name\":\"Aqua Teen Hunger Force (Seasons 1-9)\",\"size\":\"17.45 G\",\"type\":\"video/x-msvideo\",\"date\":\"1/1/2014\",\"tags\":[\"aqua teen hunger force\",\"adult swim\",\"check it out\",\"steve brule\",\"for your health\"]}" +
                "]";

    }


}
