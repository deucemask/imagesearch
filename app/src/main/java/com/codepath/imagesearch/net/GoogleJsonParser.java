package com.codepath.imagesearch.net;

import android.media.Image;
import android.util.Log;

import com.codepath.imagesearch.adapters.ImageGridAdapter;
import com.codepath.imagesearch.models.ImageItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by dmaskev on 10/18/15.
 */
public class GoogleJsonParser {

    public static String parseResponseDetails(JSONObject json) {
        String details = "";
        try {
            details = json.getString("responseDetails");
        } catch (JSONException e) {
        }

        return details;
    }

    public static List<ImageItem> parse(JSONObject json) {
        if(json == null) {
            return Collections.emptyList();
        }

        List<ImageItem> items;
        try {
            JSONArray results = json.getJSONObject("responseData").getJSONArray("results");
            items = new ArrayList<>(results.length());
            for(int i = 0; i < results.length(); i++) {
                JSONObject resItem = (JSONObject)results.get(i);
                ImageItem item = new ImageItem(resItem.getString("unescapedUrl"), resItem.getString("titleNoFormatting"));
                item.tbWidth = resItem.getString("tbWidth");
                item.tbHeight = resItem.getString("tbHeight");
                item.width = resItem.getString("width");
                item.height = resItem.getString("height");
                item.tbUrl = resItem.getString("tbUrl");
                items.add(item);
            }

        } catch(JSONException e) {
            Log.e(GoogleJsonParser.class.getSimpleName(), "Failed to parse google json", e);
            items = Collections.emptyList();
        }

        return items;
//        return Arrays.asList(new ImageItem("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQTxj0qSBKOYOuCAiTIkMQLn1qjJFs4784f538sNRZPUpqz0sYfOQ", ""),
//                new ImageItem("https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcTyEISW2nLoZeSyaM5AphLifdJJVLEM1cnX6qS4FYenmwxBCVWKpA", ""),
//                new ImageItem("https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcS9jURAW3Ve55hs6Z00EiXgLVbD7mG6k9l9UphTeVEODdmYgA8h", ""),
//                new ImageItem("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS46onp8fXpbXX2TSAp1vZZ8Qe9WVaSwB8AYvi2E-PnQO8cLp4W", "")
//        );
    }

}
