package com.easyrouteapp.exception;

/**
 * Created by fernando on 11/08/2016.
 */
public class NoContentException extends ConnectionServiceException {

    public NoContentException(String message) {
        super(message);
    }

    public NoContentException(String message, Throwable e) {
        super(message, e);
    }

}
