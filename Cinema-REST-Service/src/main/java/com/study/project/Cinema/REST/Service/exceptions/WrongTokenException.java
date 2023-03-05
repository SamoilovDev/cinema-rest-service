package com.study.project.Cinema.REST.Service.exceptions;

public class WrongTokenException extends RuntimeException {
    public WrongTokenException(String message) {
        super(message);
    }
}
