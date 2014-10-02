package com.bpf;

import org.apache.commons.fileupload.FileItem;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class Tags {

    public static ArrayList<String> getTags(HttpServletRequest request, FileItem fileItem){

        String sTags = request.getHeader("tags");
        sTags = String.format("%s %s", sTags, fileItem.getName());
        sTags = sTags.replace(" ", ",");
        sTags = sTags.replace(".", ",");
        sTags = sTags.replace("(", ",");
        sTags = sTags.replace(")", ",");
        sTags = sTags.replace("#", ",");
        String[] temp = sTags.split(",");
        ArrayList<String> tags = new ArrayList<String>();

        for (int x = 0; x < temp.length; x++){
            temp[x] = temp[x].trim().toLowerCase(); // all tags must be lower case
            if ((null == temp[x]) || (temp[x].equals(""))){ continue; } // empty string?
            if (tags.contains(temp[x])){ continue; }    // get rid of duplicates
            if (!isValidTag(temp[x])){ continue; }  // is this a valid tag?

            getParts(temp[x], "-", tags);   // "tone-loc" actually adds 3 tags.. "tone-loc", "tone", "loc"

            if (!tags.contains(temp[x])){
                tags.add(temp[x]);
            }

        }

        return tags;

    }

    public static ArrayList<String> getParts(String tag, String delimiter, ArrayList<String> tags){

        tags = null == tags ? tags = new ArrayList<String>() : tags;

        String[] parts = tag.split(delimiter);
        for (String part : parts) {
            String temp = part.trim();
            if (!temp.equals("")){
                if (!tags.contains(temp)){
                    tags.add(temp);
                }
            }
        }

        return tags;

    }

    private static boolean isValidTag(String tag){

        ArrayList<String> invalidTags = new ArrayList<String>();
        invalidTags.add("the");
        invalidTags.add("this");
        invalidTags.add("and");
        invalidTags.add("a");
        invalidTags.add("&");
        invalidTags.add("-");
        invalidTags.add("tag1");
        invalidTags.add("tag2");
        invalidTags.add("tag3");
        invalidTags.add("#");

        return !(invalidTags.contains(tag));

    }

}
