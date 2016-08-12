package com.easyrouteapp.helper;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GeocoderHelper {
	
	public static boolean isGeocoderAvailable() {
        return Geocoder.isPresent();
	}

	public static double[] doGeocoding(Context context, String addressStr) throws IOException {
		Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addressList = geocoder.getFromLocationName(addressStr, 1);

        if (addressList != null && addressList.size() > 0) {
            Address address = addressList.get(0);
            return new double[] { address.getLatitude(), address.getLongitude() };

        } else {
            return null;
        }
	}

	public static String doReverseGeocoding(Context context, double latitude, double longitude) throws IOException {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);

        if (addressList != null && addressList.size() > 0) {
            Address address = addressList.get(0);

            String s = "";

            for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                String line = address.getAddressLine(i);
                s += line + "\n";
            }

            return s.toString().trim();

        } else {
            return null;
        }
	}
}
