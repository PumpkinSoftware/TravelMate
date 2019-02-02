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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        /*Loading images with glide */
        ImageView img = (ImageView) view.findViewById(R.id.profile);

        GlideApp.with(getContext())
                .load(R.drawable.girl)
                .into(img);

        return view;
    }
}