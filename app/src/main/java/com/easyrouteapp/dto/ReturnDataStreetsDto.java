package com.easyrouteapp.dto;

import com.easyrouteapp.domain.EntityBase;
import com.easyrouteapp.domain.RouteStreet;

import java.util.List;

/**
 * Created by fernando on 11/08/2016.
 */
public class ReturnDataStreetsDto {

    private List<RouteStreet> rows;
    private Integer rowsAffected;

    public List<RouteStreet> getRows() {
        return rows;
    }

    public void setRows(List<RouteStreet> rows) {
        this.rows = rows;
    }

    public Integer getRowsAffected() {
        return rowsAffected;
    }

    public void setRowsAffected(Integer rowsAffected) {
        this.rowsAffected = rowsAffected;
    }
}
