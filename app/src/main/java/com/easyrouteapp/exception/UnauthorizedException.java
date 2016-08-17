package com.easyrouteapp.exception;

/**
 * Created by fernando on 11/08/2016.
 */
public class UnauthorizedException extends ConnectionServiceException {

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Throwable e) {
        super(message, e);
    }

}
