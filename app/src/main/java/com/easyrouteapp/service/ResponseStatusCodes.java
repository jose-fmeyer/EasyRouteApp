package com.easyrouteapp.service;

import com.easyrouteapp.exception.BadRequestException;
import com.easyrouteapp.exception.ForbiddenException;
import com.easyrouteapp.exception.InternalServerErrorException;
import com.easyrouteapp.exception.NoContentException;
import com.easyrouteapp.exception.NotFoundException;
import com.easyrouteapp.exception.NotModifiedException;
import com.easyrouteapp.exception.UnauthorizedException;

/**
 * Created by fernando on 16/08/2016.
 */
public enum ResponseStatusCodes {
    SUCESS(200, null, "connection.response.sucess"),
    CREATED(201, null, "connection.response.created"),
    NO_CONTENT(204, NoContentException.class, "connection.response.nocontent"),
    NOT_MODIFIED(304, NotModifiedException.class, "connection.response.notmodified"),
    BAD_REQUEST(400, BadRequestException.class, "connection.response.badrequest"),
    UNAUTHORIZED(401, UnauthorizedException.class, "connection.response.unauthorized"),
    FORBIDDEN(403, ForbiddenException.class, "connection.response.forbidden"),
    NOT_FOUND(404, NotFoundException.class, "connection.response.notfound"),
    INTERNAL_SERVER_ERROR(500, InternalServerErrorException.class, "connection.response.intertalerror");

    private int statusCode;
    private Class exceptionClass;
    private String messageResponse;

    ResponseStatusCodes(int statusCode, Class exceptionClass, String messageResponse) {
        this.statusCode = statusCode;
        this.exceptionClass = exceptionClass;
        this.messageResponse = messageResponse;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Class getExceptionClass() {
        return exceptionClass;
    }

    public String getMessageResponse() {
        return messageResponse;
    }
}
