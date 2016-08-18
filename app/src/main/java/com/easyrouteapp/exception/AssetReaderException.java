package com.easyrouteapp.exception;

/**
 * Created by fernando on 16/08/2016.
 */
public class AssetReaderException extends RuntimeException {

    public AssetReaderException(String message) {
        super(message);
    }

    public AssetReaderException(Throwable e) {
        super(e);
    }

    public AssetReaderException(String message, Throwable e) {
        super(message, e);
    }
}
