package com.easyrouteapp.event;

import com.easyrouteapp.domain.EntityBase;

import java.util.List;

/**
 * Created by fernando on 11/08/2016.
 */
public class ReturnLoadDataEvent <T extends EntityBase>{

    private List<T> data;

    public ReturnLoadDataEvent(List<T> data, Class owner){
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }
}
