package ru.utair.student2.todo.demo.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.utair.student2.todo.demo.exception.NotFoundException;

@ControllerAdvice
public class NotFoundAdvice {
    @ResponseBody
    @ExceptionHandler (NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String notFoundHandler (NotFoundException e) {
        return e.getMessage();
    }
}
