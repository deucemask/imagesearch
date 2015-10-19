package com.codepath.imagesearch.activities;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.codepath.imagesearch.R;
import com.codepath.imagesearch.models.ImageItem;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import static com.codepath.imagesearch.activities.ImageSearchActivity.INTENT_DATA_IMAGE;
import static com.codepath.imagesearch.util.MediaStoreHelper.shareImage;

public class ImageViewActivity extends AppCompatActivity {

    private MenuItem miImageLoader;
    private ShareActionProvider miShareAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_image_view, menu);

        miImageLoader = menu.findItem(R.id.miImageLoading);

        MenuItem item = menu.findItem(R.id.miShare);
        // Fetch reference to the share action provider
        miShareAction = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        ImageItem image = getIntent().getParcelableExtra(INTENT_DATA_IMAGE);
        if (image != null) {
            loadImageAndCallback(image);
        }


        return super.onCreateOptionsMenu(menu);
    }

    private void loadImageAndCallback(final ImageItem image) {
        miImageLoader.setVisible(true);
        final ImageView ivImage = (ImageView) findViewById(R.id.ivFullImage);
        Picasso.with(this).load(image.url).into(ivImage, new Callback() {
            @Override
            public void onSuccess() {
                Log.d(ImageViewActivity.class.getSimpleName(), "loaded image " + image.url);
                miImageLoader.setVisible(false);
                miShareAction.setShareIntent(shareImage(ImageViewActivity.this, ivImage, "image/*", "Share Image"));
            }

            @Override
            public void onError() {
                Log.e(ImageViewActivity.class.getSimpleName(), "image not loaded " + image.url);
                miImageLoader.setVisible(false);
            }
        });
    }
}
