package com.study.project.Cinema.REST.Service.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "The password is wrong!")
public class WrongPasswordException extends RuntimeException {
}
