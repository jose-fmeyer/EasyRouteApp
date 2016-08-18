package com.easyrouteapp.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.easyrouteapp.fragment.RouteStreetsFragment;
import com.easyrouteapp.fragment.RouteTimetablesFragment;

/**
 * Created by fernando on 11/08/2016.
 */
public class TabsAdapter extends FragmentPagerAdapter {
    public static final int INDEX_TAB_STREETS = 0;
    public static final int INDEX_TAB_TIMETABLES_WEEKDAY = 1;
    public static final int INDEX_TAB_TIMETABLES_WEEKEND = 2;

    private int numberOfTabs;

    public TabsAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case INDEX_TAB_STREETS: fragment = new RouteStreetsFragment();
            break;
            case INDEX_TAB_TIMETABLES_WEEKDAY: fragment = new RouteTimetablesFragment();
            break;
            case INDEX_TAB_TIMETABLES_WEEKEND: fragment = new RouteTimetablesFragment();
            break;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
