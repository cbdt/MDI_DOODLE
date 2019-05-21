package com.mdi.backend.controllers;

public class PollNotFoundException extends RuntimeException {

    public PollNotFoundException(String exception) {
        super(exception);
    }
}
