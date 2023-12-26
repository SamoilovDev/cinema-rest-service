package com.study.project.Cinema.REST.Service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "The password is wrong!")
public class WrongPasswordException extends RuntimeException {
}
