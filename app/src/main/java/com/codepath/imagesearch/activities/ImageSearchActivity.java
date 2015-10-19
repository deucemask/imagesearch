package com.codepath.imagesearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.codepath.imagesearch.adapters.ImageGridAdapter;
import com.codepath.imagesearch.R;
import com.codepath.imagesearch.models.ImageItem;
import com.codepath.imagesearch.net.GoogleImageApi;
import com.codepath.imagesearch.util.EndlessScrollListener;
import com.codepath.imagesearch.util.ParcelableStringMap;

import java.util.ArrayList;
import java.util.List;

public class ImageSearchActivity extends AppCompatActivity {

    public static final String INTENT_DATA_FILTERS = "filters";
    public static final String INTENT_DATA_IMAGE = "image";
    private GoogleImageApi googleApi;
    private static final int IMAGES_PER_PAGE = 8;
    private static final int SCROLL_MAX_PAGES = 7;

    private static final int ACTIVITY_RES_CODE_SETTINGS = 999;
    private int curPage = 1;
//    private GridView gvImages;
    private List<ImageItem> images;
    private GoogleImageApi.Callback googleApiCallback;
    private ImageGridAdapter igAdapter;
    private ParcelableStringMap filters;
    private EditText etQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etQuery = (EditText) findViewById(R.id.etQuery);
        filters = new ParcelableStringMap();
        images = new ArrayList<ImageItem>();

        GridView gvImages = (GridView) findViewById(R.id.gvImages);
        igAdapter = new ImageGridAdapter(this, images);
        gvImages.setAdapter(igAdapter);
//        gridview.setAdapter(new ImageGridAdapter(this, Arrays.asList("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQTxj0qSBKOYOuCAiTIkMQLn1qjJFs4784f538sNRZPUpqz0sYfOQ",
//                "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcTyEISW2nLoZeSyaM5AphLifdJJVLEM1cnX6qS4FYenmwxBCVWKpA",
//                "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcS9jURAW3Ve55hs6Z00EiXgLVbD7mG6k9l9UphTeVEODdmYgA8h",
//                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS46onp8fXpbXX2TSAp1vZZ8Qe9WVaSwB8AYvi2E-PnQO8cLp4W"
//                )));

        googleApi = new GoogleImageApi(IMAGES_PER_PAGE);
        googleApiCallback = new GoogleImageApi.Callback() {
            @Override
            public void onSuccess(List<ImageItem> images) {
                igAdapter.addAll(images);
            }

            @Override
            public void onFailure(int statusCode, String responseString, Throwable throwable) {
                Log.e(ImageSearchActivity.class.getSimpleName(), "Failed on google search api", throwable);
                Toast.makeText(ImageSearchActivity.this, "Failed to load images", Toast.LENGTH_LONG).show();
            }
        };

        gvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageItem image = igAdapter.getItem(position);
                Intent intent = new Intent(ImageSearchActivity.this, ImageViewActivity.class);
                intent.putExtra(INTENT_DATA_IMAGE, image);
                startActivity(intent);
            }
        });

        //TODO: limit infinite scroll to 8 pages
        gvImages.setOnScrollListener(new EndlessScrollListener(IMAGES_PER_PAGE) {
            @Override
            public boolean onLoadMore(int page) {
                Log.d(ImageSearchActivity.class.getSimpleName(), "scroll page " + page);
                if(page > SCROLL_MAX_PAGES) {
                    return false;
                }

                googleApi.searchImages(etQuery.getText().toString(), page, filters.getMap(), googleApiCallback);
                return true;
            }
        });


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    public void onSearch(View button) {
        igAdapter.clear();
        googleApi.searchImages(etQuery.getText().toString(), 0, filters.getMap(), googleApiCallback);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SearchSettingsActivity.class);
            intent.putExtra(INTENT_DATA_FILTERS, filters);
            startActivityForResult(intent, ACTIVITY_RES_CODE_SETTINGS);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == ACTIVITY_RES_CODE_SETTINGS) {
            ParcelableStringMap filters = (ParcelableStringMap) data.getParcelableExtra(INTENT_DATA_FILTERS);
            if(filters == null || filters.getMap() == null || filters.getMap().size() == 0) {
                Log.e(this.getClass().getSimpleName(), "Filter data is missing");
                return;
            }

            this.filters = filters;

        }
    }

}
