package com.study.project.Cinema.REST.Service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Wrong token!")
public class WrongTokenException extends RuntimeException {
}
