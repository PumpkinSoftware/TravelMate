package com.example.pumpkinsoftware.travelmate;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Utils {

    public static final String SERVER_PATH = "https://debugtm.herokuapp.com/";

    public static final String GIF_LOADING_URL = "https://firebasestorage.googleapis.com/v0/b/travelmate-ee239.appspot.com/o/Other%2FLoadingGif.gif?alt=media&token=add995b2-ee17-4dd5-9a9c-3dd101427c2f";

    public static final String FIREBASE_DYNAMIC_LINK_PATH = "https://travelmateapp.page.link";


    public static byte[] compressImage(final String IMAGE_PATH, ImageView image_view){
        image_view.setDrawingCacheEnabled(true);
        image_view.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) image_view.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        return data;
    }

}
