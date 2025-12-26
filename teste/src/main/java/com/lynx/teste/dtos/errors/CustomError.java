package com.lynx.teste.dtos.errors;

import java.time.Instant;

public class CustomError {

    private Integer timestamp;
    private Integer status;
    private String message;
    public String path;


    public CustomError(Integer timestamp, Integer status, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.path = path;
    }

    public CustomError(Instant now, int value, String message, String requestURI) {
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}


