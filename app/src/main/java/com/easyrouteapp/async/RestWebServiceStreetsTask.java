package com.easyrouteapp.async;

import android.content.Context;
import android.os.AsyncTask;
import com.easyrouteapp.dto.FilterDto;
import com.easyrouteapp.dto.ReturnDataStreetsDto;
import com.easyrouteapp.event.DetailLoadDataEvent;
import com.easyrouteapp.event.LoadDataServiceErrorEvent;
import com.easyrouteapp.event.RefreshStartLoadingEvent;
import com.easyrouteapp.event.RefreshStopEvent;
import com.easyrouteapp.exception.ConnectionServiceException;
import com.easyrouteapp.helper.ResponseServiceConnectionValidatorHelper;
import com.easyrouteapp.helper.RetrofitConnectionHelper;
import com.easyrouteapp.service.RoutesStreetsService;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by fernando on 11/08/2016.
 */
public class RestWebServiceStreetsTask extends AsyncTask<FilterDto, Void, Void> {

    private Context context;

    public RestWebServiceStreetsTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        EventBus.getDefault().post(new RefreshStartLoadingEvent());
    }

    @Override
    protected Void doInBackground(FilterDto... params) {
        RetrofitConnectionHelper retHelper = new RetrofitConnectionHelper(context);
        Retrofit retrofit = retHelper.getRetrofitConnection();
        RoutesStreetsService routesService = retrofit.create(RoutesStreetsService.class);
        Call<ReturnDataStreetsDto> listCall = routesService.listRoutesStreets(retHelper.getBasicAuthorization(), params[0]);
        listCall.enqueue(new Callback<ReturnDataStreetsDto>() {
            @Override
            public void onResponse(Call<ReturnDataStreetsDto> call, Response<ReturnDataStreetsDto> response) {
                try {
                    ResponseServiceConnectionValidatorHelper validatorHelper = new ResponseServiceConnectionValidatorHelper(context);
                    EventBus.getDefault().post(new RefreshStopEvent());
                    validatorHelper.validateResponseService(response);
                    EventBus.getDefault().post(new DetailLoadDataEvent<>(response.body().getRows()));
                } catch (ConnectionServiceException e) {
                    EventBus.getDefault().post(new LoadDataServiceErrorEvent(e.getMessage(), e));
                }
            }

            @Override
            public void onFailure(Call<ReturnDataStreetsDto> call, Throwable e) {
                EventBus.getDefault().post(new LoadDataServiceErrorEvent(e.getMessage(), e));
            }
        });
        return null;
    }
}
