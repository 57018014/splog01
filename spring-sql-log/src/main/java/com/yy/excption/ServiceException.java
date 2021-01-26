package com.yy.excption;


public class ServiceException extends RuntimeException {

    public int code;

    public ServiceException(int errorCode, String errorMessage) {
        super(errorMessage);
        this.code = errorCode;
    }

    public int getCode() {
        return code;
    }
}
