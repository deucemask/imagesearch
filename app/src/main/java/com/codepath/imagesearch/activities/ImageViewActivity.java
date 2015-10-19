package com.codepath.imagesearch.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.codepath.imagesearch.R;
import com.codepath.imagesearch.models.ImageItem;
import com.squareup.picasso.Picasso;

import static com.codepath.imagesearch.activities.ImageSearchActivity.INTENT_DATA_IMAGE;

public class ImageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        ImageItem image = getIntent().getParcelableExtra(INTENT_DATA_IMAGE);

        if(image != null) {
            Picasso.with(this).load(image.url).into((ImageView)findViewById(R.id.ivFullImage));
        }

    }
}
