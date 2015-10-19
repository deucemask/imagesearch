package com.codepath.imagesearch.net;

import android.net.Uri;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.codepath.imagesearch.models.ImageItem;
import com.google.common.base.Joiner;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

import static com.codepath.imagesearch.net.GoogleJsonParser.parseResponseDetails;

/**
 * Created by dmaskev on 10/18/15.
 */
public class GoogleImageApi {
    private static final int MAX_PAGE_SIZE_ALLOWED = 8;
    private static final String URL_SEARCH_IMAGES = "https://ajax.googleapis.com/ajax/services/search/images";


    private int pageSize;
    //    private int callTimes;
    private AsyncHttpClient api;

    public interface Callback {
        void onSuccess(String query, List<ImageItem> images);

        void onFailure(String query, int statusCode, String responseString, Throwable throwable);
    }


    public GoogleImageApi(int pageSize) {
        this.pageSize = pageSize;
        if (pageSize > MAX_PAGE_SIZE_ALLOWED) {
            throw new IllegalStateException("Maximum pageSize can be " + MAX_PAGE_SIZE_ALLOWED);
        }
//        this.callTimes = Double.valueOf(Math.ceil(pageSize / MAX_PAGE_SIZE_ALLOWED)).intValue();
//        if(this.callTimes < 1) {
//            this.callTimes = 1;
//        }
        api = new AsyncHttpClient();
    }

    /**
     * @param query
     * @param page     - 1-based page number
     * @param callback
     */
    public void searchImages(final String query, int page, Map<String, String> filters, final Callback callback) {


        //TODO: so far assuming pageSize is mod MAX_PAGE_SIZE_ALLOWED. Should handle more generic case.
//        int cappedSize = MAX_PAGE_SIZE_ALLOWED;
        int start = page * this.pageSize;
        String urlFilters = Joiner.on('&').withKeyValueSeparator("=").join(filters);
//        for(int i = 0; i < this.callTimes; i++) {
        String url = getApiUrl(query, this.pageSize, start, urlFilters);

        Log.d(this.getClass().getSimpleName(), "calling search api " + url);

        api.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d(GoogleImageApi.class.getSimpleName(), statusCode + " api response " + response);
                if (statusCode == 200) {
                    List<ImageItem> images = GoogleJsonParser.parse(response);
                    callback.onSuccess(query, images);
                } else {
                    callback.onFailure(query, statusCode, parseResponseDetails(response), null);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                callback.onFailure(query, statusCode, responseString, throwable);
            }
        });
//        }


//        Arrays.asList(new ImageItem("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQTxj0qSBKOYOuCAiTIkMQLn1qjJFs4784f538sNRZPUpqz0sYfOQ", "one"),
//                "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcTyEISW2nLoZeSyaM5AphLifdJJVLEM1cnX6qS4FYenmwxBCVWKpA",
//                "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcS9jURAW3Ve55hs6Z00EiXgLVbD7mG6k9l9UphTeVEODdmYgA8h",
//                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS46onp8fXpbXX2TSAp1vZZ8Qe9WVaSwB8AYvi2E-PnQO8cLp4W"
//        )


    }

    private String getApiUrl(String query, int pageSize, int start, String filterUrlString) {
        StringBuilder sb = new StringBuilder(URL_SEARCH_IMAGES)
                .append("?v=1.0")
                .append("&q=").append(Uri.encode(query))
                .append("&rsz=").append(pageSize)
                .append("&start=").append(start);

        if (filterUrlString != null && !filterUrlString.isEmpty()) {
            sb.append("&").append(filterUrlString);
        }

        return sb.toString();
    }


}
