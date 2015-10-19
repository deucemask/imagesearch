package com.codepath.imagesearch.activities;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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
    public static final String FILTER_NONE = "none";
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
            populateSpinner(filters, COLOR, spColor);
            populateSpinner(filters, IMAGE_SIZE, spSize);
            populateSpinner(filters, IMAGE_TYPE, spType);
            if(filters.containsKey(SITE.getUrlParam())) {
                etSite.setText(filters.get(SITE.getUrlParam()));
            }
        }

    }

    private void populateSpinner(ParcelableStringMap filterValueMap, GoogleSearchFilter filter, Spinner spinner) {
        String colorValue = filterValueMap.get(filter.getUrlParam());
        if(colorValue != null) {
            int colorPos = ((ArrayAdapter<String>)spinner.getAdapter()).getPosition(colorValue);
            spinner.setSelection(colorPos);
        }
    }

    public void onSave(View view) {
        ParcelableStringMap filters = new ParcelableStringMap();
        filters.clear();
        putIfExist(filters, COLOR.getUrlParam(), spColor.getSelectedItem().toString());
        putIfExist(filters, IMAGE_SIZE.getUrlParam(), spSize.getSelectedItem().toString());
        putIfExist(filters, IMAGE_TYPE.getUrlParam(), spType.getSelectedItem().toString());
        putIfExist(filters, SITE.getUrlParam(), etSite.getText().toString());

        Intent intent = new Intent(this, ImageSearchActivity.class);
        intent.putExtra(INTENT_DATA_FILTERS, filters);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void putIfExist(ParcelableStringMap map, String key, String value) {
        if(value != null && value.length() > 0 && !value.equals(FILTER_NONE)) {
            map.put(key, value);
        }
    }
}
