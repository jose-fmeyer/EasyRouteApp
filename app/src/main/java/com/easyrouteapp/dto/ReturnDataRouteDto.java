package com.easyrouteapp.dto;

import com.easyrouteapp.domain.EntityBase;
import com.easyrouteapp.domain.Route;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by fernando on 11/08/2016.
 */
public class ReturnDataRouteDto {

    private List<Route> rows;
    private Integer rowsAffected;

    public List<Route> getRows() {
        return rows;
    }

    public void setRows(List<Route> rows) {
        this.rows = rows;
    }

    public Integer getRowsAffected() {
        return rowsAffected;
    }

    public void setRowsAffected(Integer rowsAffected) {
        this.rowsAffected = rowsAffected;
    }
}
