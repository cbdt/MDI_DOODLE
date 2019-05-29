package com.mdi.backend.controllers;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String exception) {
        super(exception);
    }
}
