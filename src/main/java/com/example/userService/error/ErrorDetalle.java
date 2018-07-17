package com.example.userService.error;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by marcelo on 07-03-18
 */
@Getter
@Setter
public class ErrorDetalle {

    private String codigo;
    private String detalle;
    private String mensaje;

    public ErrorDetalle() {
    }

    public ErrorDetalle( String codigo, String detalle, String mensaje) {

        this.codigo = codigo;
        this.detalle = detalle;
        this.mensaje = mensaje;
    }


}
