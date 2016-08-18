package com.easyrouteapp.exception;

/**
 * Created by fernando on 11/08/2016.
 */
public class BadRequestException extends ConnectionServiceException {

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable e) {
        super(message, e);
    }

}
