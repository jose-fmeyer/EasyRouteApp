package com.easyrouteapp.helper;

import android.util.Base64;

/**
 * Created by fernando on 11/08/2016.
 */
public class BasicAuthorizationHelper {

    public static String getBasicAuthorizationString(String user, String password){
        StringBuilder stringAuth = new StringBuilder();
        stringAuth.append(user).append(":").append(password);
        StringBuilder builder = new StringBuilder(new String(Base64.encode(stringAuth.toString().getBytes(), Base64.DEFAULT)));
        builder.deleteCharAt(builder.length() -1);
        return "Basic ".concat(builder.toString());
    }
}
