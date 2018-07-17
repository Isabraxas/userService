package com.example.userService.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by marcelo on 07-03-18
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoEncontradoRestException extends RuntimeException{

    private ErrorDetalle errorDetalle;        // contiene informacion del error

    public NoEncontradoRestException() {
    }

    public NoEncontradoRestException(String message) {
        super(message);
    }

    public NoEncontradoRestException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public NoEncontradoRestException(ErrorDetalle errorDetalle) {
        this.errorDetalle = errorDetalle;
    }

    public NoEncontradoRestException(String message, ErrorDetalle errorDetalle) {
        super(message);
        this.errorDetalle = errorDetalle;
    }

    public NoEncontradoRestException(String message, Throwable throwable, ErrorDetalle errorDetalle) {
        super(message, throwable);
        this.errorDetalle = errorDetalle;
    }

    public ErrorDetalle getErrorDetalle() {
        return errorDetalle;
    }

    public void setErrorDetalle(ErrorDetalle errorDetalle) {
        this.errorDetalle = errorDetalle;
    }
}
