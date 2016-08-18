package com.easyrouteapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fernando on 11/08/2016.
 */
public class FilterDto implements Serializable {

    private Param params;

    public static class Builder {
        private FilterDto filter;

        public Builder() {
            this.filter = new FilterDto();
            filter.params = new Param();
        }

        public Builder withStopNameParam(String stopName){
            filter.params.setStopName(stopName);
            return this;
        }

        public Builder withRouteId(Integer routeId){
            filter.params.setRouteId(routeId);
            return this;
        }

        public FilterDto build(){
            return filter;
        }
    }

    public Param getParams() {
        return params;
    }

    public void setParams(Param params) {
        this.params = params;
    }
}
