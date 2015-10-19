package com.codepath.imagesearch.models;

/**
 * Created by dmaskev on 10/18/15.
 */
public enum GoogleSearchFilter {
    IMAGE_SIZE("imgsz"), IMAGE_TYPE("imgtype"), COLOR("imgcolor"), SITE("as_sitesearch");

    private String urlParam;
    private GoogleSearchFilter(String urlParam) {
        this.urlParam = urlParam;
    }

    public String getUrlParam() {
        return this.urlParam;
    }
}
