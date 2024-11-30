package com.devweb2.project.tasks.exceptions;

import org.springframework.http.HttpStatus;

public class ApiRequestException extends RuntimeException{
    
    private static final long serialVersionUID = 1L;
    private final String message;
    private final HttpStatus statusCode;

    public ApiRequestException(String message, HttpStatus statusCode){
        super(message);
        this.message = message;
        this.statusCode = statusCode;
    }   

    public String getMessage(){
        return this.message;
    }

    public HttpStatus getStatusCode(){
        return this.statusCode;
    }

}
