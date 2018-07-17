package com.example.userService.error;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ErrorTransferencia {

    private Timestamp fecha;
    private String estado;
    private ErrorDetalle error;


}
