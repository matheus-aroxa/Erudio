package com.miromorii.cursoerudio.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RequiredObjectIsNullException extends RuntimeException {

    public RequiredObjectIsNullException(String message) {
        super(message);
    }

    public RequiredObjectIsNullException(){
        super("Can't persist null object");
    }
}
