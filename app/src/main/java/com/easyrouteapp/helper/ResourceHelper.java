package com.easyrouteapp.helper;

import android.content.Context;

/**
 * Created by fernando on 11/08/2016.
 */
public class ResourceHelper {

    public static String getString(Context context, int resourceId) {
        return context.getResources().getString(resourceId);
    }
}
