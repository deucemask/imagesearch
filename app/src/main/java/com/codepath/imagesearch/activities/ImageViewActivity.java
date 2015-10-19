package com.codepath.imagesearch.activities;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.codepath.imagesearch.R;
import com.codepath.imagesearch.models.ImageItem;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.codepath.imagesearch.activities.ImageSearchActivity.INTENT_DATA_IMAGE;

public class ImageViewActivity extends AppCompatActivity {

    private MenuItem miImageLoader;
    private ImageItem image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);


        image = getIntent().getParcelableExtra(INTENT_DATA_IMAGE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_image_view, menu);

        miImageLoader = menu.findItem(R.id.miImageLoading);

        if(image != null) {
            miImageLoader.setVisible(true);
            Picasso.with(this).load(image.url).into((ImageView)findViewById(R.id.ivFullImage)
                    , new Callback() {
                @Override
                public void onSuccess() {
                    Log.d(ImageViewActivity.class.getSimpleName(), "loaded image " + image.url);
                    miImageLoader.setVisible(false);
                }

                @Override
                public void onError() {
                    Log.e(ImageViewActivity.class.getSimpleName(), "image not loaded " + image.url);
                    miImageLoader.setVisible(false);
                }
            });
        }


        return super.onCreateOptionsMenu(menu);
    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        // Store instance of the menu item containing progress
//        miImageLoader = menu.findItem(R.id.miImageLoading);
//        // Extract the action-view from the menu item
////        ProgressBar v =  (ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);
//        // Return to finish
//        return super.onPrepareOptionsMenu(menu);
//    }
}
