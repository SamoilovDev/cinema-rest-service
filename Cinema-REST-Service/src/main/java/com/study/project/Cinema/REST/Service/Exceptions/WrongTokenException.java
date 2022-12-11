package com.study.project.Cinema.REST.Service.Exceptions;

public class WrongTokenException extends RuntimeException {
    public WrongTokenException(String message) {
        super(message);
    }
}
