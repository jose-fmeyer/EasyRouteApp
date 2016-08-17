package com.easyrouteapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.easyrouteapp.async.GeocodingTask;
import com.easyrouteapp.async.RestWebServiceRoutesTask;
import com.easyrouteapp.domain.EntityBase;
import com.easyrouteapp.dto.FilterDto;
import com.easyrouteapp.event.GeoCodeErrorEvent;
import com.easyrouteapp.event.GeoCodeLoadedEvent;
import com.easyrouteapp.event.RefreshStartLoadingEvent;
import com.easyrouteapp.event.RefreshStopEvent;
import com.easyrouteapp.event.ReverseGeoCodeLoadedEvent;
import com.easyrouteapp.event.StartDetailRouteEvent;
import com.easyrouteapp.fragment.RoutesFragment;
import com.easyrouteapp.helper.StatusNetworkConnectionHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "[MainActivity]";

    private Toolbar searchToolbar;
    private RoutesFragment fragRoutes;
    private List<EntityBase> routes = new ArrayList<>();
    private MenuItem itemSearch;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(MainActivity.this);

        searchToolbar = (Toolbar) findViewById(R.id.tb_search);
        searchToolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(searchToolbar);

        progressBar = (ProgressBar) findViewById(R.id.progress_main);

        fragRoutes = (RoutesFragment) getSupportFragmentManager().findFragmentById(R.id.routes_fragment_container);
        if (fragRoutes == null) {
            fragRoutes = new RoutesFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.routes_fragment_container, fragRoutes, "routesFrag");
            ft.commit();
        }
        seachRoutesByQuery(getIntent());
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(MainActivity.this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStartRefresh(RefreshStartLoadingEvent event) {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStopRefresh(RefreshStopEvent event) {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        this.itemSearch = menu.findItem(R.id.action_searchable_activity);
        SearchView searchView = getSearchView(itemSearch);
        searchView.setSearchableInfo( searchManager.getSearchableInfo( getComponentName() ) );
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        addFloatButtonActionListener((FloatingActionButton) findViewById(R.id.fab));
        return true;
    }

    private void addFloatButtonActionListener(FloatingActionButton floatButton) {
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!StatusNetworkConnectionHelper.verifyConnection(MainActivity.this) ) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.message_without_con), Toast.LENGTH_LONG).show();
                    return;
                }
                itemSearch.collapseActionView();
                new GeocodingTask(getApplicationContext()).execute(getResources().getString(R.string.name_default_city_map));
            }
        });
    }

    private SearchView getSearchView(MenuItem item) {
        return (SearchView) item.getActionView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventStartDetail(StartDetailRouteEvent event) {
        Log.i("LOG", "RoutesFragment.this.onEventMainThread()");
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.ROUTE_FOR_DETAIL, event.getRoute());
        startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent( intent );
        seachRoutesByQuery( intent );
    }

    public void seachRoutesByQuery( Intent intent ) {
        if(!StatusNetworkConnectionHelper.verifyConnection(this) ){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.message_without_con), Toast.LENGTH_LONG).show();
            return;
        }
        if( Intent.ACTION_SEARCH.equalsIgnoreCase( intent.getAction() ) ){
            String stopName = intent.getStringExtra( SearchManager.QUERY );
            searchToolbar.setTitle(stopName);
            fragRoutes.clearRecycleViewData();
            RestWebServiceRoutesTask restTask = new RestWebServiceRoutesTask(getApplicationContext());
            restTask.execute(new FilterDto.Builder().withStopNameParam("%" + stopName + "%").build());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMapAdressReturn(ReverseGeoCodeLoadedEvent event) {
        if(event.getAddress() == null){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.location_not_founded), Toast.LENGTH_LONG).show();
            return;
        }
        String stopName = event.getAddress().substring(0, event.getAddress().indexOf(",")).toUpperCase();
        searchToolbar.setTitle(stopName);
        RestWebServiceRoutesTask restTask = new RestWebServiceRoutesTask(getApplicationContext());
        restTask.execute(new FilterDto.Builder().withStopNameParam("%" + stopName + "%").build());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReturnDefaultCoordinatesToOpenMap(GeoCodeLoadedEvent event) {
        Intent intent = new Intent(MainActivity.this, MapActivity.class);
        intent.putExtra(MapActivity.EXTRA_DEFAULT_MAP_NAME, event.getLatLng());
        startActivity(intent);
    }

    @Subscribe
    public void onErrorGeoCode(GeoCodeErrorEvent event) {
        Log.e(TAG, event.getError().getMessage(), event.getError());
        Toast.makeText(getApplicationContext(), event.getMessage(), Toast.LENGTH_LONG).show();
    }
}
