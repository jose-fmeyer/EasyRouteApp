package com.easyrouteapp.fragment;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.easyrouteapp.R;
import com.easyrouteapp.adapter.RouteAdapter;
import com.easyrouteapp.component.CustomRecycleView;
import com.easyrouteapp.domain.Route;
import com.easyrouteapp.event.LoadDataServiceErrorEvent;
import com.easyrouteapp.event.ReturnLoadDataEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class RoutesFragment extends RefreshableFragment {
    private static final String TAG_LOG = "[RoutesFragment]";

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
        //routesRV.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        routesRV.setLayoutManager(llm);

        View emptyView = fragmentView.findViewById(R.id.list_empty_view);
        routesRV.setEmptyView(emptyView);

        routesRV.setAdapter(new RouteAdapter(getActivity(), new ArrayList<Route>()));

        mSwipeRefreshLayout = (SwipeRefreshLayout) fragmentView.findViewById(R.id.srl_swiperoute);
        return fragmentView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(RoutesFragment.this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadRoutesData(ReturnLoadDataEvent event) {
        List<Route> routes = event.getData();
        clearRecycleViewData();
        addRecycleViewData(routes);
        getSwipeRefreshLayout().setEnabled(false);
    }

    @Subscribe
    public void onLoadDataError(LoadDataServiceErrorEvent event) {
        Log.e(TAG_LOG, event.getError().getMessage(), event.getError());
        Toast.makeText(getActivity().getApplicationContext(), event.getMessage(), Toast.LENGTH_LONG).show();
    }

    public void addRecycleViewData(List<Route> routes) {
        for (int position = 0; position < routes.size(); position++) {
            ((RouteAdapter)routesRV.getAdapter()).addListItem(routes.get(position), position);
        }
    }

    public void clearRecycleViewData(){
        ((RouteAdapter)routesRV.getAdapter()).clearData();
    }

    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }
}
