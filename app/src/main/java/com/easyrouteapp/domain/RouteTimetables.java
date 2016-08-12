package com.easyrouteapp.domain;

/**
 * Created by fernando on 12/08/2016.
 */
public class RouteTimetables extends EntityBase {

    private String calendar;
    private String time;

    public String getCalendar() {
        return calendar;
    }

    public void setCalendar(String calendar) {
        this.calendar = calendar;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
