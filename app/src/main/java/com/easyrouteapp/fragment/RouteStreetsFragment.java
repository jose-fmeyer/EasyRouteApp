package com.easyrouteapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easyrouteapp.R;
import com.easyrouteapp.adapter.FragmentAdapterDataSize;
import com.easyrouteapp.adapter.RouteStreetsAdapter;
import com.easyrouteapp.domain.RouteStreet;

import java.util.ArrayList;
import java.util.List;

public class RouteStreetsFragment extends Fragment implements FragmentAdapterDataSize {

    private RecyclerView recyclerViewStreets;
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

        return fragmentView;
    }

    public void addRecycleViewData(List<RouteStreet> streets) {
        for (int position = 0; position < streets.size(); position++) {
            ((RouteStreetsAdapter)recyclerViewStreets.getAdapter()).addListItem(streets.get(position), position);
        }
    }

    @Override
    public Integer getAdapterDataSize() {
        return recyclerViewStreets.getAdapter().getItemCount();
    }
}
