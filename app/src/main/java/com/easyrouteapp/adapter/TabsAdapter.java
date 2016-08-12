package com.easyrouteapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.easyrouteapp.R;
import com.easyrouteapp.fragment.RouteStreetsFragment;
import com.easyrouteapp.fragment.RouteTimetablesFragment;
import com.easyrouteapp.helper.ResourceHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fernando on 11/08/2016.
 */
public class TabsAdapter extends FragmentPagerAdapter {
    public static final int POS_ROUTE_STREET_TAB = 0;
    public static final int POS_ROUTE_TIMETABLES_TAB = 1;

    private Context context;
    private List<String> titles = new ArrayList<>();

    public TabsAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        loadTitles();
    }

    private void loadTitles(){
        titles.add(ResourceHelper.getString(context, R.string.tab_streets));
        titles.add(ResourceHelper.getString(context, R.string.tab_timetables));
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0 : fragment = new RouteStreetsFragment();
            break;
            case 1 : fragment = new RouteTimetablesFragment();
            break;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
