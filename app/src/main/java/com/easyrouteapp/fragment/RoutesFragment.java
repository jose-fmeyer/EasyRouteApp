package com.easyrouteapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easyrouteapp.R;
import com.easyrouteapp.adapter.RouteAdapter;
import com.easyrouteapp.component.CustomRecycleView;
import com.easyrouteapp.domain.Route;
import com.easyrouteapp.event.RefreshStartLoadingEvent;
import com.easyrouteapp.event.RefreshStopEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class RoutesFragment extends Fragment {

    private CustomRecycleView routesRV;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    private View fragmentView;

    public RoutesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        EventBus.getDefault().register(RoutesFragment.this);

        fragmentView = inflater.inflate(R.layout.fragment_routes, container, false);
        routesRV = (CustomRecycleView) fragmentView.findViewById(R.id.rv_routes);
        routesRV.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        routesRV.setLayoutManager(llm);

        View emptyView = fragmentView.findViewById(R.id.list_empty_view);
        routesRV.setEmptyView(emptyView);

        routesRV.setAdapter(new RouteAdapter(getActivity(), new ArrayList<Route>()));

        return fragmentView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(RoutesFragment.this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStartRefresh(RefreshStartLoadingEvent event) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) fragmentView.findViewById(R.id.srl_swiperoute);
        if(!mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStopRefresh(RefreshStopEvent event){
        mSwipeRefreshLayout = (SwipeRefreshLayout) fragmentView.findViewById(R.id.srl_swiperoute);
        if(mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.setEnabled(false);
        }
    }

    public void addRecycleViewData(List<Route> routes) {
        for (int pos = 0; pos < routes.size(); pos++) {
            ((RouteAdapter)routesRV.getAdapter()).addListItem(routes.get(pos), pos);
        }
    }

    public void clearRecycleViewData(){
        ((RouteAdapter)routesRV.getAdapter()).clearData();
    }

}
