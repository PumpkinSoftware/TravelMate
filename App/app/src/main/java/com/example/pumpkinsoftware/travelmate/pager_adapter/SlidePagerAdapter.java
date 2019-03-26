package com.example.pumpkinsoftware.travelmate.pager_adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.pumpkinsoftware.travelmate.content_fragment_travels.Tab1;
import com.example.pumpkinsoftware.travelmate.content_fragment_travels.Tab2;
import com.example.pumpkinsoftware.travelmate.content_fragment_travels.Tab3;

public class SlidePagerAdapter extends FragmentPagerAdapter {

    public SlidePagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int index) {
        Fragment fragment;
        if (index == 0)         fragment = new Tab1();
        //else if (index == 1)    fragment = new Tab2();
        else                    fragment = new Tab3();
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int index) {
        String title = null;
        if (index == 0)         title = "In corso";
       // else if (index == 1)    title = "Preferiti";
        else                    title = "Conclusi";
        return title;
    }

}
