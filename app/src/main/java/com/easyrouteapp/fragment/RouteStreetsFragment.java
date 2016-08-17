package com.easyrouteapp.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easyrouteapp.R;
import com.easyrouteapp.adapter.FragmentAdapterDataSize;
import com.easyrouteapp.adapter.RouteStreetsAdapter;
import com.easyrouteapp.domain.RouteStreet;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class RouteStreetsFragment extends RefreshableFragment implements FragmentAdapterDataSize {

    private RecyclerView recyclerViewStreets;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    private View fragmentView;

    public RouteStreetsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_route_streets, container, false);
        recyclerViewStreets = (RecyclerView) fragmentView.findViewById(R.id.rv_routes_streets);
        recyclerViewStreets.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewStreets.setLayoutManager(llm);

        recyclerViewStreets.setAdapter(new RouteStreetsAdapter(getActivity(), new ArrayList<RouteStreet>()));
        mSwipeRefreshLayout = (SwipeRefreshLayout) fragmentView.findViewById(R.id.srl_swipestreet);

        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!EventBus.getDefault().isRegistered(RouteStreetsFragment.this)){
            EventBus.getDefault().register(RouteStreetsFragment.this);
        }
        getSwipeRefreshLayout().setEnabled(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(EventBus.getDefault().isRegistered(RouteStreetsFragment.this)){
            EventBus.getDefault().unregister(RouteStreetsFragment.this);
        }
    }

    public void addRecycleViewData(List<RouteStreet> streets) {
        for (int position = 0; position < streets.size(); position++) {
            ((RouteStreetsAdapter)recyclerViewStreets.getAdapter()).addListItem(streets.get(position), position);
        }
        getSwipeRefreshLayout().setEnabled(false);
    }

    @Override
    public Integer getAdapterDataSize() {
        return recyclerViewStreets.getAdapter().getItemCount();
    }

    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }
}
