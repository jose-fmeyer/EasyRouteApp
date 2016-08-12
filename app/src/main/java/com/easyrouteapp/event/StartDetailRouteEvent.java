package com.easyrouteapp.event;

import com.easyrouteapp.domain.Route;

/**
 * Created by fernando on 10/08/2016.
 */
public class StartDetailRouteEvent {

    private Route route;

    public StartDetailRouteEvent(Route route){
        this.route = route;
    }

    public Route getRoute() {
        return route;
    }
}
