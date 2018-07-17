package com.example.userService.error;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by marcelo on 09-03-18
 * Clase para generalizar los mensajes de error
 */
@Getter
@Setter
public class EntidadError {


    protected String estado;
    protected ErrorDetalle error;

    public EntidadError() {
    }

    public EntidadError( String estado, ErrorDetalle error) {

        this.estado = estado;
        this.error = error;
    }


}
