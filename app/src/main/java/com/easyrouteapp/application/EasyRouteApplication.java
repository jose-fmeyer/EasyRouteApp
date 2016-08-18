package com.easyrouteapp.application;

import android.app.Application;

import com.easyrouteapp.helper.ResourceAssetPropertyHelper;

/**
 * Created by fernando on 17/08/2016.
 */
public class EasyRouteApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ResourceAssetPropertyHelper.initInstance(getApplicationContext());
    }
}
