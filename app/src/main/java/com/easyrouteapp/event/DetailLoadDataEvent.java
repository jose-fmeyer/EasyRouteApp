package com.easyrouteapp.event;

import com.easyrouteapp.domain.EntityBase;

import java.util.List;

/**
 * Created by fernando on 12/08/2016.
 */
public class DetailLoadDataEvent <T extends EntityBase>{

    private List<T> data;

    public DetailLoadDataEvent(List<T> data){
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }

}
