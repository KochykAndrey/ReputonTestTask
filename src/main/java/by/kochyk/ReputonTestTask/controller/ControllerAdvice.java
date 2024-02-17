package by.kochyk.ReputonTestTask.controller;

import by.kochyk.ReputonTestTask.domain.exception.ExceptionBody;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ResponseStatusException.class)
    public ExceptionBody handleResponseStatusException(final ResponseStatusException e) {
        return new ExceptionBody(e.getStatusCode().value(), e.getReason());
    }

}
