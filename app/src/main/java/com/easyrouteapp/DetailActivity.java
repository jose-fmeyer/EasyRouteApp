package com.easyrouteapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.easyrouteapp.adapter.FragmentAdapterDataSize;
import com.easyrouteapp.adapter.TabsAdapter;
import com.easyrouteapp.async.RestWebServiceStreetsTask;
import com.easyrouteapp.async.RestWebServiceTimetablesTask;
import com.easyrouteapp.component.listener.OnTabSelectedListenerAdapter;
import com.easyrouteapp.domain.Route;
import com.easyrouteapp.domain.RouteStreet;
import com.easyrouteapp.domain.RouteTimetables;
import com.easyrouteapp.dto.FilterDto;
import com.easyrouteapp.event.DetailLoadDataEvent;
import com.easyrouteapp.event.LoadDataServiceErrorEvent;
import com.easyrouteapp.fragment.RouteStreetsFragment;
import com.easyrouteapp.fragment.RouteTimetablesFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG_LOG = "[DetailActivity]";
    public static final String ROUTE_FOR_DETAIL = "route_for_detail";

    private Toolbar detailToolbar;
    private TabLayout tabLayout ;
    private ViewPager viewPager;
    private Route routeForDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        EventBus.getDefault().register(DetailActivity.this);
        setRouteForDetail();

        detailToolbar = (Toolbar) findViewById(R.id.tb_detail);
        detailToolbar.setLogo(R.mipmap.ic_launcher);
        detailToolbar.setTitle(routeForDetail.getLongName());
        setSupportActionBar(detailToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        tabLayout = (TabLayout) findViewById(R.id.detail_tabs);
        prepareTabLayout();
        viewPager = (ViewPager) findViewById(R.id.view_page_tabs);
        prepareViewPager();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTabData(TabsAdapter.INDEX_TAB_STREETS);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(DetailActivity.this);
    }

    private void loadTabData(int position) {
        Fragment fragDataSize = getSupportFragmentManager().findFragmentByTag(
                "android:switcher:"+R.id.view_page_tabs+":".concat(String.valueOf(position)));
        if(fragDataSize != null && ((FragmentAdapterDataSize)fragDataSize).getAdapterDataSize() > 0){
            return;
        }
        if(viewPager.getCurrentItem() == TabsAdapter.INDEX_TAB_STREETS) {
            RestWebServiceStreetsTask task = new RestWebServiceStreetsTask(getApplicationContext());
            task.execute(new FilterDto.Builder().withRouteId(routeForDetail.getId().intValue()).build());
            return;
        }
        RestWebServiceTimetablesTask task = new RestWebServiceTimetablesTask(getApplicationContext());
        task.execute(new FilterDto.Builder().withRouteId(routeForDetail.getId().intValue()).build());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadRoutesStreetData(DetailLoadDataEvent event) {
        if(viewPager.getCurrentItem() == TabsAdapter.INDEX_TAB_STREETS) {
            RouteStreetsFragment frag = (RouteStreetsFragment)  getSupportFragmentManager().findFragmentByTag(
                    "android:switcher:"+R.id.view_page_tabs+":0");
            List<RouteStreet> routes = event.getData();
            frag.addRecycleViewData(routes);
            return;
        }
        Pair<List<RouteTimetables>, List<RouteTimetables>> filteredTimeTables = getWeekDayRoutes(event.getData());
        RouteTimetablesFragment fragTab1 = (RouteTimetablesFragment)  getSupportFragmentManager().findFragmentByTag(
                "android:switcher:"+R.id.view_page_tabs+":1");
        fragTab1.addRecycleViewData(filteredTimeTables.first);
        RouteTimetablesFragment fragTab2 = (RouteTimetablesFragment)  getSupportFragmentManager().findFragmentByTag(
                "android:switcher:"+R.id.view_page_tabs+":2");
        fragTab2.addRecycleViewData(filteredTimeTables.second);
    }

    private Pair getWeekDayRoutes(List<RouteTimetables> routeTimetables){
        List<RouteTimetables> weekDayRoutes = new ArrayList<>();
        List<RouteTimetables> weekendRoutes = new ArrayList<>();
        for (RouteTimetables routeTime: routeTimetables) {
            if(getResources().getString(R.string.label_weekday).equals(routeTime.getCalendar())){
                weekDayRoutes.add(routeTime);
                continue;
            }
            weekendRoutes.add(routeTime);
        }
        return new Pair(weekDayRoutes, weekendRoutes);
    }

    @Subscribe
    public void onLoadDataError(LoadDataServiceErrorEvent event) {
        Log.e(TAG_LOG, event.getError().getMessage(), event.getError());
        Toast.makeText(getApplicationContext(), event.getMessage(), Toast.LENGTH_LONG).show();
    }

    private void setRouteForDetail() {
        if(getIntent().getExtras() != null){
            routeForDetail = (Route) getIntent().getExtras().get(DetailActivity.ROUTE_FOR_DETAIL);
        }
    }

    private void prepareTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_streets)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_timetables)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_weekend_timetables)));
        tabLayout.setOnTabSelectedListener(new OnTabSelectedListenerAdapter(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                loadTabData(tab.getPosition());
            }
        });
    }

    private void prepareViewPager() {
        viewPager.setAdapter(new TabsAdapter(getSupportFragmentManager(), tabLayout.getTabCount()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
        }
        return true;
    }
}
