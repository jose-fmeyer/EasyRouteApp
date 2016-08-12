package com.easyrouteapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easyrouteapp.R;
import com.easyrouteapp.adapter.FragmentAdapterDataSize;
import com.easyrouteapp.adapter.RouteAdapter;
import com.easyrouteapp.adapter.RouteStreetsAdapter;
import com.easyrouteapp.component.CustomRecycleView;
import com.easyrouteapp.domain.Route;
import com.easyrouteapp.domain.RouteStreet;
import com.easyrouteapp.event.RefreshStartLoadingEvent;
import com.easyrouteapp.event.RefreshStopEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class RouteStreetsFragment extends Fragment implements FragmentAdapterDataSize {

    private RecyclerView recyclerViewStreets;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    private View fragmentView;

    public RouteStreetsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(RouteStreetsFragment.this);
        fragmentView = inflater.inflate(R.layout.fragment_route_streets, container, false);
        recyclerViewStreets = (RecyclerView) fragmentView.findViewById(R.id.rv_routes_streets);
        recyclerViewStreets.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewStreets.setLayoutManager(llm);

        recyclerViewStreets.setAdapter(new RouteStreetsAdapter(getActivity(), new ArrayList<RouteStreet>()));

        return fragmentView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(RouteStreetsFragment.this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStartRefresh(RefreshStartLoadingEvent event){
        mSwipeRefreshLayout = (SwipeRefreshLayout) fragmentView.findViewById(R.id.srl_swipestreet);
        if(!mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStopRefresh(RefreshStopEvent event){
        mSwipeRefreshLayout = (SwipeRefreshLayout) fragmentView.findViewById(R.id.srl_swipestreet);
        if(mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mSwipeRefreshLayout.setEnabled(false);
    }

    public void addRecycleViewData(List<RouteStreet> streets) {
        for (int pos = 0; pos < streets.size(); pos++) {
            ((RouteStreetsAdapter)recyclerViewStreets.getAdapter()).addListItem(streets.get(pos), pos);
        }
    }

    public void clearRecycleViewData(){
        ((RouteStreetsAdapter)recyclerViewStreets.getAdapter()).clearData();
    }

    @Override
    public Integer getAdapterDataSize() {
        return recyclerViewStreets.getAdapter().getItemCount();
    }
}
