package com.example.pumpkinsoftware.travelmate;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        context = getContext();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            //boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
            RequestQueue mRequestQueue = Volley.newRequestQueue(context);
            final GetUserByUid server =  new GetUserByUid(context);
            server.getUserFromServer(URL+uid, mRequestQueue, new ServerCallback() {
                        @Override
                        public void onSuccess(JSONObject response) {
                            loadUser(server);
                        }
                    }
            );


        }

        else {
            /*Loading images with glide */
            ImageView img = (ImageView) view.findViewById(R.id.profile);

            GlideApp.with(context)
                    .load(R.drawable.girl)
                    .into(img);
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
        String ns=mUser.getName()+ " "+ mUser.getSurname(); //così non rompe
        txt.setText(ns);

        txt = view.findViewById(R.id.bio);
        txt.setText(mUser.getDescr());

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