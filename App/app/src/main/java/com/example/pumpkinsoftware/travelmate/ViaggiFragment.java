package com.example.pumpkinsoftware.travelmate;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pumpkinsoftware.travelmate.pager_adapter.SlidePagerAdapter;

public class ViaggiFragment extends Fragment implements View.OnClickListener {
    public FloatingActionButton fabBtn;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)  {
        View inflate = inflater.inflate(R.layout.fragment_viaggi, container, false);
        Context context = inflate.getContext();

        //copiato dal tutorial prima che funzionasse da fare tentativi se serve
        fabBtn = (FloatingActionButton) inflater.inflate(R.layout.fragment_viaggi, container, false).findViewById(R.id.floatbutton);
        fabBtn.setOnClickListener(this);
        //fine

        viewPager = (ViewPager) inflate.findViewById(R.id.pager);
        PagerAdapter mAdapter = new SlidePagerAdapter(getFragmentManager());
        viewPager.setAdapter(mAdapter);
        tabLayout = inflate.findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);

        return inflate;

    }
    //necessario per implements del tutorial
    public void onClick(View v) {
        Intent intent = new Intent(ViaggiFragment.this.getActivity(), CreationTrip.class);
        startActivity(intent);
    };
    //il metodo run this è dichiarato nel main, l'activity genitore

}