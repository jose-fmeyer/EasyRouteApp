package com.easyrouteapp.helper;

import android.content.Context;

import com.easyrouteapp.exception.AssetReaderException;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by fernando on 16/08/2016.
 */
public class RetrofitConnectionHelper {

    private ResourceAssetPropertyHelper assetHelper;
    private Context context;

    public RetrofitConnectionHelper(Context context) {
        this.assetHelper = new ResourceAssetPropertyHelper(context);
        this.context = context;
    }

    public Retrofit getRetrofitConnection() {
        String baseEndpoint = assetHelper.getString(ResourceAssetPropertyHelper.PropertiesKeys.CONNECTION_BASE_ENDPOINT);
        return getRetrofitService(baseEndpoint);
    }

    public String getBasicAuthorization(){
        String user = assetHelper.getString(ResourceAssetPropertyHelper.PropertiesKeys.CONNECTION_USER);
        String password = assetHelper.getString(ResourceAssetPropertyHelper.PropertiesKeys.CONNECTION_PASSWORD);
        return BasicAuthorizationHelper.getBasicAuthorizationString(user, password);
    }

    private Retrofit getRetrofitService(String baseEndPoint) throws AssetReaderException {
        return new Retrofit.Builder()
                .baseUrl(baseEndPoint)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }
}
