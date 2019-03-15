package com.example.pumpkinsoftware.travelmate;

import android.content.Context;
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
import com.example.pumpkinsoftware.travelmate.client_server_interaction.GetUserByUid;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.ServerCallback;
import com.example.pumpkinsoftware.travelmate.glide.GlideApp;
import com.example.pumpkinsoftware.travelmate.user.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONObject;


public class ProfileFragment extends Fragment {
    private final static String URL = "https://debugtm.herokuapp.com/user/getUserByUid?userUid=";
    private Context context;
    private View view;
    private Boolean isReady;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        context = getContext();

        final RelativeLayout layout = view.findViewById(R.id.layout);
        layout.setVisibility(View.INVISIBLE);
        final ProgressBar progressBar = view.findViewById(R.id.indeterminateBar);
        progressBar.setVisibility(View.VISIBLE);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            //boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
            RequestQueue mRequestQueue = Volley.newRequestQueue(context);
            final GetUserByUid server =  new GetUserByUid(context, progressBar);
            server.getUserFromServer(URL+uid, mRequestQueue, new ServerCallback() {
                        @Override
                        public void onSuccess(JSONObject response) {
                            loadUser(server);
                            layout.setVisibility(View.VISIBLE);
                        }
            });
        }

        else {
            /*Loading images with glide */
            ImageView img = (ImageView) view.findViewById(R.id.profile);

            GlideApp.with(context)
                    .load(R.drawable.blank_avatar)
                    .into(img);

            ImageView img2=(ImageView) view.findViewById(R.id.header_cover_image);

            Glide.with(context)
                    .load(R.drawable.blank_cover)
                    .into(img2);
        }

        return view;
    }

    private boolean loadUser(GetUserByUid server) {
        User mUser = server.getUser();

        if(mUser == null) return false;

        ImageView img = (ImageView) view.findViewById(R.id.profile);
        GlideApp.with(context)
                .load(mUser.getPhotoProfile())
                .into(img);

        img = view.findViewById(R.id.header_cover_image);
        GlideApp.with(context)
                .load(mUser.getCover())
                .into(img);

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
}