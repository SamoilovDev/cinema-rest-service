package com.study.project.Cinema.REST.Service.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Wrong token!")
public class WrongTokenException extends RuntimeException {
}
