package com.easyrouteapp.helper;

import android.content.Context;

import com.easyrouteapp.exception.AssetReaderException;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by fernando on 16/08/2016.
 */
public class RetrofitConnectionHelper {

    private RetrofitConnectionHelper(){};

    public static Retrofit getRetrofitConnection() {
        String baseEndpoint = ResourceAssetPropertyHelper.getString(ResourceAssetPropertyHelper.PropertiesKeys.CONNECTION_BASE_ENDPOINT);
        return getRetrofitService(baseEndpoint);
    }

    public static String getBasicAuthorization(){
        String user = ResourceAssetPropertyHelper.getString(ResourceAssetPropertyHelper.PropertiesKeys.CONNECTION_USER);
        String password = ResourceAssetPropertyHelper.getString(ResourceAssetPropertyHelper.PropertiesKeys.CONNECTION_PASSWORD);
        return BasicAuthorizationHelper.getBasicAuthorizationString(user, password);
    }

    private static Retrofit getRetrofitService(String baseEndPoint) throws AssetReaderException {
        return new Retrofit.Builder()
                .baseUrl(baseEndPoint)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }
}
