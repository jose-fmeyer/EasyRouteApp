package com.easyrouteapp.service;

import com.easyrouteapp.dto.FilterDto;
import com.easyrouteapp.dto.ReturnDataRouteDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by fernando on 11/08/2016.
 */
public interface RoutesService {

    @Headers({
            "Accept: application/json",
            "X-AppGlu-Environment: staging"
    })
    @POST("findRoutesByStopName/run")
    Call<List<ReturnDataRouteDto>> listRoutes(@Header("Authorization") String authorization, @Body FilterDto filtro);
}
