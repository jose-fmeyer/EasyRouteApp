package com.easyrouteapp.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;

import com.easyrouteapp.event.RefreshStartLoadingEvent;
import com.easyrouteapp.event.RefreshStopEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by fernando on 15/08/2016.
 */
public abstract class RefreshableFragment extends Fragment {

    protected abstract SwipeRefreshLayout getSwipeRefreshLayout();

    @Override
    public void onStart() {
        super.onStart();
        addSwipeTypeRefreshListener();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStartRefresh(RefreshStartLoadingEvent event) {
        if(isVisible()){
            getSwipeRefreshLayout().setRefreshing(true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStopRefresh(RefreshStopEvent event) {
        if(isVisible()) {
            getSwipeRefreshLayout().setRefreshing(false);
        }
    }

    private void addSwipeTypeRefreshListener() {
        getSwipeRefreshLayout().setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getSwipeRefreshLayout().setRefreshing(false);
            }
        });
    }

}
