package com.example.pumpkinsoftware.travelmate.pager_adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.pumpkinsoftware.travelmate.content_fragment_travels.ContentFragmentTravels;

public class SlidePagerAdapter extends FragmentPagerAdapter {

    public SlidePagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int index) {
        Fragment fragment = null;
        if (index == 0)         fragment = new ContentFragmentTravels();
        else if (index == 1)    fragment = new ContentFragmentTravels();
        else                    fragment = new ContentFragmentTravels();
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int index) {
        String title = null;
        if (index == 0)         title = "Futuri";
        else if (index == 1)    title = "Preferiti";
        else                    title = "Passati";
        return title;
    }

}
