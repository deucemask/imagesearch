package com.codepath.imagesearch.util;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.OutputStream;

/**
 * Created by dmaskev on 10/19/15.
 */
public class MediaStoreHelper {
    public static Intent shareImage(Context ctx, ImageView imageView, String mimeType, String subject) {
        Intent sharingIntent = null;
        try {
            ContentValues values = new ContentValues(1);
            values.put(MediaStore.Images.Media.MIME_TYPE, mimeType);
            Uri uri = ctx.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            OutputStream out = ctx.getContentResolver().openOutputStream(uri);

            Bitmap bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);

            sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType(mimeType);
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
            sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sharingIntent;
    }
    
}
