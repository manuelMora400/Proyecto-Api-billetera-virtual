package com.autenticacion.login.exception;

public class NoExisteID extends RuntimeException {
    public NoExisteID (String mensaje){
        super(mensaje);
    }
}
