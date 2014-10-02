package com.bpf;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import org.bson.types.ObjectId;
import java.text.SimpleDateFormat;

import java.util.Date;

public class Json {

    public static String getJson(DBCursor cursor){

        String json = "[";

        while (cursor.hasNext()){

            DBObject upload = cursor.next();

            Object[] keySet = ((BasicDBObject) upload).keySet().toArray();

            json += "{";

            boolean firstProp = true;

            for (int x = 0; x < keySet.length; x++){

                String key = keySet[x].toString();
                Object val = upload.get(key);

                if ((val instanceof String) || (val instanceof ObjectId)) {
                    if (firstProp){ firstProp = false; } else { json += ","; }
                    json += String.format("\"%s\":\"%s\"", key, val.toString());
                }
                else if (val instanceof BasicDBList) {
                    if (firstProp){ firstProp = false; } else { json += ","; }
                    json += String.format("\"%s\":%s", key, getStringArray((BasicDBList)val));
                }
                else if (val instanceof Date) {
                    if (firstProp){ firstProp = false; } else { json += ","; }
                    json += String.format("\"%s\":\"%s\"", key, getDateString((Date)val));
                }
                else if (val instanceof Long) {
                    if (firstProp){ firstProp = false; } else { json += ","; }
                    json += String.format("\"%s\":%s", key, val.toString());
                }

            }

            json += "}";
            if (cursor.hasNext()) { json += ","; }

        }

        json += "]";

        return json;

    }

    private static String getStringArray(BasicDBList strings){

        String json = "[";

        boolean firstProp = true;

        for (Object o : strings) {
            if (firstProp){ firstProp = false; } else { json += ","; }
            json += String.format("\"%s\"", o.toString());
        }

        json += "]";

        return json;

    }

    private static String getDateString(Date date){
        String dateString = new SimpleDateFormat("M/d/yyyy").format(date);
        return dateString;
        //return "2/16/2014";
    }

}
