package com.easyrouteapp.async;

import android.content.Context;
import android.os.AsyncTask;

import com.easyrouteapp.MainActivity;
import com.easyrouteapp.R;
import com.easyrouteapp.domain.Route;
import com.easyrouteapp.dto.FilterDto;
import com.easyrouteapp.dto.ReturnDataRouteDto;
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
public class RestWebServiceRoutesTask extends AsyncTask<FilterDto, Void, ReturnDataRouteDto> {

    private Context context;

    public RestWebServiceRoutesTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
            EventBus.getDefault().post(new RefreshStartLoadingEvent());
    }

    @Override
    protected ReturnDataRouteDto doInBackground(FilterDto... params) {
        try {
            return (ReturnDataRouteDto) HttpConnectionHelper.makePostConnection(params[0],
                    ResourceHelper.getString(context, R.string.end_point_routes), ReturnDataRouteDto.class);
        } catch (ConnectionServiceException e) {
            EventBus.getDefault().post(new LoadDataServiceErrorEvent(e.getMessage(), e));
        }
        return null;
    }

    @Override
    protected void onPostExecute(ReturnDataRouteDto dataDto) {
        EventBus.getDefault().post(new ReturnLoadDataEvent<Route>(dataDto.getRows(), MainActivity.class));
        EventBus.getDefault().post(new RefreshStopEvent());
    }
}
