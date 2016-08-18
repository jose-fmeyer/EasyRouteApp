package com.easyrouteapp.exception;

/**
 * Created by fernando on 11/08/2016.
 */
public class InternalServerErrorException extends ConnectionServiceException {

    public InternalServerErrorException(String message) {
        super(message);
    }

    public InternalServerErrorException(String message, Throwable e) {
        super(message, e);
    }

}
