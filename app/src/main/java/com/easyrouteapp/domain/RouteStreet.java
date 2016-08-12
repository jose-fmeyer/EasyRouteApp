package com.easyrouteapp.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by fernando on 12/08/2016.
 */
public class RouteStreet extends EntityBase {

    private String name;

    private Integer sequence;

    @JsonProperty(value = "route_id")
    private Long routeId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }
}
