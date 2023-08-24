package com.study.project.Cinema.REST.Service.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "The number of a row or a column is out of bounds!")
public class NumberOutOfBoundsException extends RuntimeException {
}
