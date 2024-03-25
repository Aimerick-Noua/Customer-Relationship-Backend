package com.example.crm.exceptions;

public class UserNotFoundException extends RuntimeException{
    private String message;
    public UserNotFoundException(String message){
        super(message);
        this.message = message;

    }
    public String getMessage() {
        return message;
    }
}
