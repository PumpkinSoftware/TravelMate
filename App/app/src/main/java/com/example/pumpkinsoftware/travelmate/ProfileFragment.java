package com.example.pumpkinsoftware.travelmate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.GetUserByUid;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.ServerCallback;
import com.example.pumpkinsoftware.travelmate.glide.GlideApp;
import com.example.pumpkinsoftware.travelmate.user.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONObject;


public class ProfileFragment extends Fragment {
    private final static String URL = "https://debugtm.herokuapp.com/user/getUserByUid?userUid=";
    public final static String EXTRA_USER = "travelmate_extra_pf_USER";
    private Context context;
    private View view;
    private User User;
    private ImageView edit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        context = getContext();

        edit = view.findViewById(R.id.edit_image);

        // Old sdk hasn't elevation attribute
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            edit = view.findViewById(R.id.edit_image_for_old_sdk);

        edit.setVisibility(View.VISIBLE);

        final RelativeLayout layout = view.findViewById(R.id.layout);
        layout.setVisibility(View.INVISIBLE);
        final ProgressBar progressBar = view.findViewById(R.id.indeterminateBar);
        progressBar.setVisibility(View.VISIBLE);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            //boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            final String uid = user.getUid();
            RequestQueue mRequestQueue = Volley.newRequestQueue(context);
            final GetUserByUid server =  new GetUserByUid(context, progressBar);
            server.getUserFromServer(URL+uid, mRequestQueue, new ServerCallback() {
                        @Override
                        public void onSuccess(JSONObject response) {
                            User = server.getUser();
                            loadUser(User);

                            edit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(context, EditUserActivity.class);
                                    i.putExtra(EditUserActivity.EXTRA_USER, User);
                                    startActivityForResult(i, 1);
                                }
                            });
                            layout.setVisibility(View.VISIBLE);
                        }
            });
        }

        return view;
    }

    private boolean loadUser(User mUser) {

        if(mUser == null) return false;

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, EditUserActivity.class);
                i.putExtra(EditUserActivity.EXTRA_USER, User);
                startActivityForResult(i, 1);
            }
        });

        ImageView img = (ImageView) view.findViewById(R.id.profile);
        GlideApp.with(context)
                .load((mUser.getPhotoProfile().isEmpty())?R.drawable.blank_avatar:mUser.getPhotoProfile())
                .into(img);

        img = view.findViewById(R.id.header_cover_image);
        GlideApp.with(context)
                .load(mUser.getCover().isEmpty()?R.drawable.blank_cover:mUser.getCover())
                .into(img);

        calculateColor(mUser.getCover());

        TextView txt = view.findViewById(R.id.name);
        String ns = mUser.getName()+ " "+ mUser.getSurname();
        txt.setText(ns);

        txt = view.findViewById(R.id.bio);
        txt.setText(mUser.getDescr());
        // Justified text alignment
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            txt.setText(mUser.getDescr());
            txt.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        }

        else {
            final WebView v = (WebView) view.findViewById(R.id.bio_for_older_versions);
            String text = "<html><body><p align=\"justify\">";
            text+= mUser.getDescr();
            text+= "</p></body></html>";
            /*
            v.loadData(text, "text/html", "utf-8");
            v.setVisibility(View.VISIBLE);
            txt.setVisibility(View.GONE);*/
            ((WebView) v.findViewById(R.id.bio_for_older_versions)).loadData(text, "text/html; charset=UTF-8", "utf-8");
            // Now I've to change the below param of the below elements
            /*final RelativeLayout layout = view.findViewById(R.id.layout2);
            RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.BELOW, R.id.bio_for_older_versions);
            layout.setLayoutParams(params);*/
        }


        txt = view.findViewById(R.id.age2);
        txt.setText(String.valueOf(mUser.getAge()));

        txt = view.findViewById(R.id.gender2);
        txt.setText(mUser.getGender());

        txt = view.findViewById(R.id.rel2);
        txt.setText(mUser.getRelationship());

        txt = view.findViewById(R.id.email2);
        txt.setText(mUser.getEmail());

        txt = view.findViewById(R.id.n_review);
        String n="( "+(String.valueOf(mUser.getNumReviews()))+" )"; //così non rompe
        txt.setText(n);

        txt = view.findViewById(R.id.rating);
        txt.setText(String.valueOf(roundToHalf(mUser.getRank())));

        RatingBar ratingBar = view.findViewById(R.id.ratingBar);
        ratingBar.setRating((float) mUser.getRank());

        return true;
    }

    public static double roundToHalf(double d) {
        return Math.round(d * 2) / 2.0;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK) {
                User u = (User) data.getSerializableExtra(EXTRA_USER);
                User = u;
                loadUser(u);
            }
        }
    }

    private void calculateColor(String photoPath) {
        Glide.with(context)
                .asBitmap()
                .load(photoPath)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Bitmap> target, boolean b) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap bitmap, Object o, Target<Bitmap> target, DataSource dataSource,
                                                   boolean b) {
                        int brightness = calculateBrightness(bitmap, 20);
                        int iconColor;
                        if(brightness > 127)    iconColor = Color.BLACK;
                        else                    iconColor = Color.WHITE;

                        edit.setColorFilter(iconColor);
                        return false;
                    }
                }
                ).submit();
    }

    /* Calculates the (estimated) brightness of a bitmap image.
     The argument "skipPixel" defines how many pixels to skip for the brightness calculation,
     because it might be very runtime intensive to calculate brightness for every single pixel.
     Higher values result in better performance, but a more estimated result value.
     When skipPixel equals 1, the method actually calculates the real average brightness, not an estimated one.
     So "skipPixel" needs to be 1 or bigger !
     The function returns a brightness level between 0 and 255, where 0 = totally black and 255 = totally bright
    */
    private int calculateBrightness(android.graphics.Bitmap bitmap, int skipPixel) {
        int R = 0; int G = 0; int B = 0;
        int height = 5; //bitmap.getHeight();
        int width = bitmap.getWidth();
        int n = 0;
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        int half = width/2;
        for (int i = 0; i < pixels.length; i += skipPixel) {
            if((i - (i/width)*width) < half) continue; // I take only right half of image because my icon is put there
            int color = pixels[i];
            R += Color.red(color);
            G += Color.green(color);
            B += Color.blue(color);
            n++;
        }
        return (R + B + G) / (n * 3);
    }

}