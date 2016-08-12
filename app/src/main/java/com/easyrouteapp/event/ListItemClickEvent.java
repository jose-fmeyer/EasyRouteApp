package com.easyrouteapp.event;

/**
 * Created by fernando on 10/08/2016.
 */
public class ListItemClickEvent {

    private int pos;

    public ListItemClickEvent(int pos){
        this.pos = pos;
    }

    public int getPos() {
        return pos;
    }
}
