package com.study.project.Cinema.REST.Service.exceptions;

public class HasBeenAlreadyPurchasedException extends RuntimeException {
    public HasBeenAlreadyPurchasedException(String message) {
        super(message);
    }
}
