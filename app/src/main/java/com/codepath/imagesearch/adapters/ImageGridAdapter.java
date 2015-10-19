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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmaskev on 10/18/15.
 */
public class ImageGridAdapter extends ArrayAdapter<ImageItem> {
    private int imageCount;
    private static class ViewHolder {
        public ImageView ivImage;
        public TextView tvTitle;
    }

    public ImageGridAdapter(Context context, List<ImageItem> images) {
        super(context, 0, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.image_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.ivImage = (ImageView)convertView.findViewById(R.id.ivImage);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        Picasso.with(getContext()).load(getItem(position).tbUrl).into(viewHolder.ivImage);
        viewHolder.tvTitle.setText(getItem(position).title);

        return convertView;
    }
}
