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
import com.easyrouteapp.adapter.RouteTimetablesAdapter;
import com.easyrouteapp.domain.RouteTimetables;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class RouteTimetablesFragment extends RefreshableFragment implements FragmentAdapterDataSize {

    private RecyclerView recyclerViewTimetables;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    private View fragmentView;

    public RouteTimetablesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_route_timetables, container, false);
        recyclerViewTimetables = (RecyclerView) fragmentView.findViewById(R.id.rv_routes_timetables);
        recyclerViewTimetables.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewTimetables.setLayoutManager(llm);

        recyclerViewTimetables.setAdapter(new RouteTimetablesAdapter(getActivity(), new ArrayList<RouteTimetables>()));
        mSwipeRefreshLayout = (SwipeRefreshLayout) fragmentView.findViewById(R.id.srl_swipetimes);

        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!EventBus.getDefault().isRegistered(RouteTimetablesFragment.this)){
            EventBus.getDefault().register(RouteTimetablesFragment.this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(EventBus.getDefault().isRegistered(RouteTimetablesFragment.this)){
            EventBus.getDefault().unregister(RouteTimetablesFragment.this);
        }
    }

    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    public void addRecycleViewData(List<RouteTimetables> timetables) {
        for (int position = 0; position < timetables.size(); position++) {
            ((RouteTimetablesAdapter)recyclerViewTimetables.getAdapter()).addListItem(timetables.get(position), position);
        }
        getSwipeRefreshLayout().setEnabled(false);
    }

    @Override
    public Integer getAdapterDataSize() {
        return recyclerViewTimetables.getAdapter().getItemCount();
    }
}
