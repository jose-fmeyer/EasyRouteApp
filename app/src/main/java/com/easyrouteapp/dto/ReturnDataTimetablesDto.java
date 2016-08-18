package com.easyrouteapp.dto;

import com.easyrouteapp.domain.RouteStreet;
import com.easyrouteapp.domain.RouteTimetables;

import java.util.List;

/**
 * Created by fernando on 11/08/2016.
 */
public class ReturnDataTimetablesDto {

    private List<RouteTimetables> rows;
    private Integer rowsAffected;

    public List<RouteTimetables> getRows() {
        return rows;
    }

    public void setRows(List<RouteTimetables> rows) {
        this.rows = rows;
    }

    public Integer getRowsAffected() {
        return rowsAffected;
    }

    public void setRowsAffected(Integer rowsAffected) {
        this.rowsAffected = rowsAffected;
    }
}
