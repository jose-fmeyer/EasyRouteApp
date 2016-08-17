package com.easyrouteapp.helper;

import android.content.Context;
import android.support.v4.util.Pair;
import android.util.Log;

import com.easyrouteapp.exception.ConnectionServiceException;
import com.easyrouteapp.service.ResponseStatusCodes;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Response;

/**
 * Created by fernando on 16/08/2016.
 */
public class ResponseServiceConnectionValidatorHelper {

    private static final String TAG = "[ExMsgValidatorHelper]";

    private static Map<Integer, Pair<String,Class>> exceptions = new HashMap<>();
    static {
        for(ResponseStatusCodes responseCodes : ResponseStatusCodes.values()) {
            exceptions.put(responseCodes.getStatusCode(), new Pair<String, Class>(responseCodes.getMessageResponse(), responseCodes.getExceptionClass()));
        }
    }

    private Context context;

    public ResponseServiceConnectionValidatorHelper(Context context) {
        this.context = context;
    }

    public void validateResponseService(Response response) throws ConnectionServiceException {
        if(ResponseStatusCodes.SUCESS.getStatusCode() == response.code()
                || ResponseStatusCodes.CREATED.getStatusCode() == response.code()) {
            return;
        }
        Pair<String, Class> responseExceptionProperties =  exceptions.get(response.code());
        ResourceAssetPropertyHelper helperAsset = new ResourceAssetPropertyHelper(context);
        try {
            throw (ConnectionServiceException) responseExceptionProperties.second.getConstructor(String.class).newInstance(helperAsset.getString(responseExceptionProperties.first));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            Log.e(TAG, "Error on execute service connection.", e);
            throw new ConnectionServiceException("Error on execute service connection.", e);
        }
    }
}
