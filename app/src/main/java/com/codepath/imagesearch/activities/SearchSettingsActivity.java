package com.codepath.imagesearch.activities;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.codepath.imagesearch.R;
import com.codepath.imagesearch.models.GoogleSearchFilter;
import com.codepath.imagesearch.util.ParcelableStringMap;

import java.util.HashMap;
import java.util.Map;

import static com.codepath.imagesearch.activities.ImageSearchActivity.INTENT_DATA_FILTERS;
import static com.codepath.imagesearch.models.GoogleSearchFilter.COLOR;
import static com.codepath.imagesearch.models.GoogleSearchFilter.IMAGE_SIZE;
import static com.codepath.imagesearch.models.GoogleSearchFilter.IMAGE_TYPE;
import static com.codepath.imagesearch.models.GoogleSearchFilter.SITE;

public class SearchSettingsActivity extends AppCompatActivity {
    private Spinner spColor;
    private Spinner spSize;
    private Spinner spType;
    private EditText etSite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_settings);

        ParcelableStringMap filters = getIntent().getParcelableExtra(INTENT_DATA_FILTERS);


        spColor = (Spinner) findViewById(R.id.spColor);
        spSize = (Spinner) findViewById(R.id.spSize);
        spType = (Spinner) findViewById(R.id.spType);
        etSite = (EditText) findViewById(R.id.etSite);

        if(filters != null) {
            //TODO: populate default filter values
        }

    }

    public void onSave(View view) {
        //TODO: change this to parcelable map
        ParcelableStringMap filters = new ParcelableStringMap();
        filters.put(COLOR.getUrlParam(), spColor.getSelectedItem().toString());
        filters.put(IMAGE_SIZE.getUrlParam(), spSize.getSelectedItem().toString());
        filters.put(IMAGE_TYPE.getUrlParam(), spType.getSelectedItem().toString());
        filters.put(SITE.getUrlParam(), etSite.getText().toString());

        Intent intent = new Intent(this, ImageSearchActivity.class);
        intent.putExtra("filters", filters);
        setResult(RESULT_OK, intent);
        finish();
    }
}
