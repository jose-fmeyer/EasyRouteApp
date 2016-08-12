package com.easyrouteapp.helper;

import android.util.Log;

import com.easyrouteapp.dto.FilterDto;
import com.easyrouteapp.exception.ConnectionServiceException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by fernando on 11/08/2016.
 */
public class HttpConnectionHelper {

    private static final String TAG = "[HttpConnectionHelper]";

    public static Object makePostConnection(FilterDto filtro, String endPoit, Class returnClass) throws ConnectionServiceException {
        try {
            URL url = new URL(endPoit);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", "Basic V0tENE43WU1BMXVpTThWOkR0ZFR0ek1MUWxBMGhrMkMxWWk1cEx5VklsQVE2OA==");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("X-AppGlu-Environment", "staging");
            con.setRequestMethod("POST");
            ObjectMapper mapper = new ObjectMapper();
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(mapper.writeValueAsString(filtro));
            wr.flush();
            StringBuilder sb = new StringBuilder();
            int HttpResult = con.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                return mapper.readValue(sb.toString(), returnClass);
            }
            Log.e(TAG, "Error on call service: " + endPoit);
            throw new ConnectionServiceException("Error on call service. Status: ".concat(String.valueOf(HttpResult)));
        } catch (MalformedURLException e) {
            String message = "Error on call service: ";
            Log.e(TAG,  message.concat(endPoit), e);
            throw new ConnectionServiceException(message, e);
        } catch (ProtocolException e) {
            String message = "Error on call service: ";
            Log.e(TAG,  message.concat(endPoit), e);
            throw new ConnectionServiceException(message, e);
        } catch (IOException e) {
            String message = "Error on call service: ";
            Log.e(TAG,  message.concat(endPoit), e);
            throw new ConnectionServiceException(message, e);
        }
    }
}
