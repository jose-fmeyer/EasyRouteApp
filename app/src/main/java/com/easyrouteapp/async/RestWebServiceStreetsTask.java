package com.easyrouteapp.async;

import android.content.Context;
import android.os.AsyncTask;

import com.easyrouteapp.DetailActivity;
import com.easyrouteapp.R;
import com.easyrouteapp.domain.Route;
import com.easyrouteapp.domain.RouteStreet;
import com.easyrouteapp.dto.FilterDto;
import com.easyrouteapp.dto.ReturnDataRouteDto;
import com.easyrouteapp.dto.ReturnDataStreetsDto;
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
public class RestWebServiceStreetsTask extends AsyncTask<FilterDto, Void, ReturnDataStreetsDto> {

    private Context context;

    public RestWebServiceStreetsTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        EventBus.getDefault().post(new RefreshStartLoadingEvent());
    }

    @Override
    protected ReturnDataStreetsDto doInBackground(FilterDto... params) {
        try {
            return (ReturnDataStreetsDto) HttpConnectionHelper.makePostConnection(params[0],
                    ResourceHelper.getString(context, R.string.end_point_streets), ReturnDataStreetsDto.class);
        } catch (ConnectionServiceException e) {
            EventBus.getDefault().post(new LoadDataServiceErrorEvent(e.getMessage(), e));
        }
        return null;
    }

    @Override
    protected void onPostExecute(ReturnDataStreetsDto dataDto) {
        EventBus.getDefault().post(new DetailLoadDataEvent<RouteStreet>(dataDto.getRows()));
        EventBus.getDefault().post(new RefreshStopEvent());
    }
}
