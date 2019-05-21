package com.mdi.backend.controllers;

public class ChoiceNotFoundException extends RuntimeException {

    public ChoiceNotFoundException(String exception) {
        super(exception);
    }
}
