package com.easyrouteapp.helper;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.easyrouteapp.exception.AssetReaderException;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by fernando on 16/08/2016.
 */
public class ResourceAssetPropertyHelper {
    private static final String TAG = "[ResourceAssetHelper]";

    private static ResourceAssetPropertyHelper assetPropertyHelper;
    private static Properties properties;

    public static ResourceAssetPropertyHelper initInstance(Context context) {
        if(assetPropertyHelper == null) {
            assetPropertyHelper = new ResourceAssetPropertyHelper(context);
        }
        return assetPropertyHelper;
    }

    private ResourceAssetPropertyHelper(Context context) throws AssetReaderException {
        AssetManager assetManager = context.getResources().getAssets();
        this.properties = new Properties();
        try {
            this.properties.load(assetManager.open("service_connection.properties"));
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new AssetReaderException(e);
        }
    }

    public final class PropertiesKeys {
        public static final String CONNECTION_USER = "connection.user";
        public static final String CONNECTION_PASSWORD = "connection.password";
        public static final String CONNECTION_BASE_ENDPOINT= "connection.base.endpoint";
        public static final String LAT_DEFAULT_FLORIANOPOLIS= "map.lat.default.floripa";
        public static final String LONG_DEFAULT_FLORIANOPOLIS= "map.long.default.floripa";
    }

    public static String getString(String property) {
        return properties.get(property).toString();
    }
}
