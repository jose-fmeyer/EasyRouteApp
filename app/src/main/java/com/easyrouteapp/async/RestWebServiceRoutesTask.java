package com.easyrouteapp.async;

import android.content.Context;
import android.os.AsyncTask;
import com.easyrouteapp.dto.FilterDto;
import com.easyrouteapp.dto.ReturnDataRouteDto;
import com.easyrouteapp.event.LoadDataServiceErrorEvent;
import com.easyrouteapp.event.RefreshStartLoadingEvent;
import com.easyrouteapp.event.RefreshStopEvent;
import com.easyrouteapp.event.ReturnLoadDataEvent;
import com.easyrouteapp.exception.ConnectionServiceException;
import com.easyrouteapp.helper.ResponseServiceConnectionValidatorHelper;
import com.easyrouteapp.helper.RetrofitConnectionHelper;
import com.easyrouteapp.service.RoutesService;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by fernando on 11/08/2016.
 */
public class RestWebServiceRoutesTask extends AsyncTask<FilterDto, Void, Void> {

    private Context context;

    public RestWebServiceRoutesTask(Context context) {
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
        RoutesService routesService = retrofit.create(RoutesService.class);
        Call<ReturnDataRouteDto> listCall = routesService.listRoutes(retHelper.getBasicAuthorization(), params[0]);
        listCall.enqueue(new Callback<ReturnDataRouteDto>() {
            @Override
            public void onResponse(Call<ReturnDataRouteDto> call, Response<ReturnDataRouteDto> response) {
                try {
                    ResponseServiceConnectionValidatorHelper validatorHelper = new ResponseServiceConnectionValidatorHelper(context);
                    EventBus.getDefault().post(new RefreshStopEvent());
                    validatorHelper.validateResponseService(response);
                    EventBus.getDefault().post(new ReturnLoadDataEvent<>(response.body().getRows()));
                } catch (ConnectionServiceException e) {
                    EventBus.getDefault().post(new LoadDataServiceErrorEvent(e.getMessage(), e));
                }
            }

            @Override
            public void onFailure(Call<ReturnDataRouteDto> call, Throwable e) {
                EventBus.getDefault().post(new LoadDataServiceErrorEvent(e.getMessage(), e));
            }
        });
        return null;
    }
}
