package com.codepath.imagesearch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.imagesearch.R;
import com.codepath.imagesearch.models.ImageItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmaskev on 10/18/15.
 */
public class ImageGridAdapter extends ArrayAdapter<ImageItem> {
    private int imageCount;

    public ImageGridAdapter(Context context, List<ImageItem> images) {
        super(context, 0, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //TODO: add ViewHolder caching
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.image_item, parent, false);
        }

        ImageView image = (ImageView)convertView.findViewById(R.id.ivImage);
        TextView text = (TextView) convertView.findViewById(R.id.tvTitle);

//        imageView.setImageResource(0);
        //TODO: add picasso
        Picasso.with(getContext()).load(getItem(position).tbUrl).into(image);
        text.setText(getItem(position).title);

        return convertView;
    }
}
