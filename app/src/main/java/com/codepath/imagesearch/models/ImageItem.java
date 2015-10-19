package com.codepath.imagesearch.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dmaskev on 10/18/15.
 */
public class ImageItem implements Parcelable {
    public String tbUrl;
    public String tbHeight;
    public String tbWidth;
    public String url;
    public String height;
    public String width;
    public String title;

    public ImageItem(String url, String title) {
        this.url = url;
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tbUrl);
        dest.writeString(this.tbHeight);
        dest.writeString(this.tbWidth);
        dest.writeString(this.url);
        dest.writeString(this.height);
        dest.writeString(this.width);
        dest.writeString(this.title);
    }

    protected ImageItem(Parcel in) {
        this.tbUrl = in.readString();
        this.tbHeight = in.readString();
        this.tbWidth = in.readString();
        this.url = in.readString();
        this.height = in.readString();
        this.width = in.readString();
        this.title = in.readString();
    }

    public static final Creator<ImageItem> CREATOR = new Creator<ImageItem>() {
        public ImageItem createFromParcel(Parcel source) {
            return new ImageItem(source);
        }

        public ImageItem[] newArray(int size) {
            return new ImageItem[size];
        }
    };
}
