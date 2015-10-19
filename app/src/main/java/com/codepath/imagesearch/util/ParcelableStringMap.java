package com.codepath.imagesearch.util;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dmaskev on 10/18/15.
 */
public class ParcelableStringMap implements Parcelable {
    private Map<String, String> map = new HashMap<>();

    public ParcelableStringMap() {

    }

    public void put(String key, String value) {
        map.put(key, value);
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void clear() {
        map.clear();
    }

    public String get(String key) {
        return map.get(key);
    }

    public boolean containsKey(String key) {
        return map.containsKey(key);
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags){
        out.writeInt(map.size());
        for(Map.Entry<String, String> entry : map.entrySet()){
            out.writeString(entry.getKey());
            out.writeString(entry.getValue());
        }
    }

    protected ParcelableStringMap(Parcel in) {
        //initialize your map before
        int size = in.readInt();
        for(int i = 0; i < size; i++){
            String key = in.readString();
            String value = in.readString();
            map.put(key, value);
        }
    }

    public static final Creator<ParcelableStringMap> CREATOR = new Creator<ParcelableStringMap>() {
        public ParcelableStringMap createFromParcel(Parcel source) {
            return new ParcelableStringMap(source);
        }

        public ParcelableStringMap[] newArray(int size) {
            return new ParcelableStringMap[size];
        }
    };

}
