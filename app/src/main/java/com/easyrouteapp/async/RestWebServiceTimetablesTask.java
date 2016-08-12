package com.easyrouteapp.async;

import android.content.Context;
import android.os.AsyncTask;

import com.easyrouteapp.DetailActivity;
import com.easyrouteapp.R;
import com.easyrouteapp.domain.Route;
import com.easyrouteapp.domain.RouteTimetables;
import com.easyrouteapp.dto.FilterDto;
import com.easyrouteapp.dto.ReturnDataRouteDto;
import com.easyrouteapp.dto.ReturnDataTimetablesDto;
import com.easyrouteapp.event.DetailLoadDataEvent;
import com.easyrouteapp.event.LoadDataServiceErrorEvent;
import com.easyrouteapp.event.RefreshStartLoadingEvent;
import com.easyrouteapp.event.RefreshStopEvent;
import com.easyrouteapp.event.ReturnLoadDataEvent;
import com.easyrouteapp.exception.ConnectionServiceException;
import com.easyrouteapp.helper.HttpConnectionHelper;
import com.easyrouteapp.helper.ResourceHelper;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by fernando on 11/08/2016.
 */
public class RestWebServiceTimetablesTask extends AsyncTask<FilterDto, Void, ReturnDataTimetablesDto> {

    private Context context;

    public RestWebServiceTimetablesTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        EventBus.getDefault().post(new RefreshStartLoadingEvent());
    }

    @Override
    protected ReturnDataTimetablesDto doInBackground(FilterDto... params) {
        try {
            return (ReturnDataTimetablesDto) HttpConnectionHelper.makePostConnection(params[0],
                    ResourceHelper.getString(context, R.string.end_point_timetables), ReturnDataTimetablesDto.class);
        } catch (ConnectionServiceException e) {
            EventBus.getDefault().post(new LoadDataServiceErrorEvent(e.getMessage(), e));
        }
        return null;
    }

    @Override
    protected void onPostExecute(ReturnDataTimetablesDto dataDto) {
        EventBus.getDefault().post(new DetailLoadDataEvent<RouteTimetables>(dataDto.getRows()));
        EventBus.getDefault().post(new RefreshStopEvent());
    }
}
