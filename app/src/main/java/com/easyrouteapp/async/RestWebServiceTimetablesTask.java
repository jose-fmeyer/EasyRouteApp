package com.easyrouteapp.async;

import android.content.Context;
import android.os.AsyncTask;
import com.easyrouteapp.dto.FilterDto;
import com.easyrouteapp.dto.ReturnDataTimetablesDto;
import com.easyrouteapp.event.DetailLoadDataEvent;
import com.easyrouteapp.event.LoadDataServiceErrorEvent;
import com.easyrouteapp.event.RefreshStartLoadingEvent;
import com.easyrouteapp.event.RefreshStopEvent;
import com.easyrouteapp.exception.ConnectionServiceException;
import com.easyrouteapp.helper.ResponseServiceConnectionValidatorHelper;
import com.easyrouteapp.helper.RetrofitConnectionHelper;
import com.easyrouteapp.service.RoutesTimetablesService;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by fernando on 11/08/2016.
 */
public class RestWebServiceTimetablesTask extends AsyncTask<FilterDto, Void, Void> {

    private Context context;

    public RestWebServiceTimetablesTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        EventBus.getDefault().post(new RefreshStartLoadingEvent());
    }

    @Override
    protected Void doInBackground(FilterDto... params) {
        Retrofit retrofit = RetrofitConnectionHelper.getRetrofitConnection();
        RoutesTimetablesService routesService = retrofit.create(RoutesTimetablesService.class);
        Call<ReturnDataTimetablesDto> listCall = routesService.listRoutesTimetables(RetrofitConnectionHelper.getBasicAuthorization(), params[0]);
        listCall.enqueue(new Callback<ReturnDataTimetablesDto>() {
            @Override
            public void onResponse(Call<ReturnDataTimetablesDto> call, Response<ReturnDataTimetablesDto> response) {
                try {
                    EventBus.getDefault().post(new RefreshStopEvent());
                    ResponseServiceConnectionValidatorHelper.validateResponseService(response);
                    EventBus.getDefault().post(new DetailLoadDataEvent<>(response.body().getRows()));
                } catch (ConnectionServiceException e) {
                    EventBus.getDefault().post(new LoadDataServiceErrorEvent(e.getMessage(), e));
                }
            }

            @Override
            public void onFailure(Call<ReturnDataTimetablesDto> call, Throwable e) {
                EventBus.getDefault().post(new LoadDataServiceErrorEvent(e.getMessage(), e));
            }
        });
        return null;
    }
}
