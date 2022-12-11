package com.study.project.Cinema.REST.Service.Exceptions;

public class HasBeenAlreadyPurchasedException extends RuntimeException {
    public HasBeenAlreadyPurchasedException(String message) {
        super(message);
    }
}
