package com.study.project.Cinema.REST.Service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "The ticket has been already purchased!")
public class AlreadyPurchasedException extends RuntimeException {
}
