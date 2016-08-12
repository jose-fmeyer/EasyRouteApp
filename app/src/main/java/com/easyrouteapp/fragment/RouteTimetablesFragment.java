package com.easyrouteapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easyrouteapp.R;
import com.easyrouteapp.adapter.FragmentAdapterDataSize;
import com.easyrouteapp.adapter.RouteTimestableAdapter;
import com.easyrouteapp.domain.RouteTimetables;
import com.easyrouteapp.event.RefreshStartLoadingEvent;
import com.easyrouteapp.event.RefreshStopEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class RouteTimetablesFragment extends Fragment implements FragmentAdapterDataSize {

    private RecyclerView recyclerViewTimetables;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    private View fragmentView;

    public RouteTimetablesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(RouteTimetablesFragment.this);
        fragmentView = inflater.inflate(R.layout.fragment_route_timetables, container, false);
        recyclerViewTimetables = (RecyclerView) fragmentView.findViewById(R.id.rv_routes_timetables);
        recyclerViewTimetables.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewTimetables.setLayoutManager(llm);

        recyclerViewTimetables.setAdapter(new RouteTimestableAdapter(getActivity(), new ArrayList<RouteTimetables>()));

        return fragmentView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(RouteTimetablesFragment.this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStartRefresh(RefreshStartLoadingEvent event){
        mSwipeRefreshLayout = (SwipeRefreshLayout) fragmentView.findViewById(R.id.srl_swipetimes);
        if(!mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStopRefresh(RefreshStopEvent event){
        mSwipeRefreshLayout = (SwipeRefreshLayout) fragmentView.findViewById(R.id.srl_swipetimes);
        if(mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.setEnabled(false);
        }
    }

    public void addRecycleViewData(List<RouteTimetables> timetables) {
        for (int pos = 0; pos < timetables.size(); pos++) {
            ((RouteTimestableAdapter)recyclerViewTimetables.getAdapter()).addListItem(timetables.get(pos), pos);
        }
    }

    public void clearRecycleViewData(){
        ((RouteTimestableAdapter)recyclerViewTimetables.getAdapter()).clearData();
    }

    @Override
    public Integer getAdapterDataSize() {
        return recyclerViewTimetables.getAdapter().getItemCount();
    }
}
