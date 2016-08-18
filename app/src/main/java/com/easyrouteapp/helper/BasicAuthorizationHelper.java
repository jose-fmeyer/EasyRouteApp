package com.easyrouteapp.helper;

import org.apache.commons.codec.binary.Base64;

/**
 * Created by fernando on 11/08/2016.
 */
public class BasicAuthorizationHelper {

    public static String getBasicAuthorizationString(String user, String password){
        StringBuilder stringAuth = new StringBuilder();
        stringAuth.append(user).append(":").append(password);
        StringBuilder builder = new StringBuilder(new String(Base64.encodeBase64(stringAuth.toString().getBytes())));
        return "Basic ".concat(builder.toString());
    }
}
