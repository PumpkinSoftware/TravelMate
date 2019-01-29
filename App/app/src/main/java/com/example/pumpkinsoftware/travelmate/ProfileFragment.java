package com.example.pumpkinsoftware.travelmate;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.pumpkinsoftware.travelmate.glide.GlideApp;

public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        /*Loading images with glide */
        /*ImageView img = getView().findViewById(R.id.profile);

        GlideApp.with(getView().getContext())
                .load(R.drawable.girl)
                .into(img);

        img = getView().findViewById(R.id.header_cover_image);

        GlideApp.with(this)
                .load(R.drawable.amsterdam)
                .into(img);*/

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}