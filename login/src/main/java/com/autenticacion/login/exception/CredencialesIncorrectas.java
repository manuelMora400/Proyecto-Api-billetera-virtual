package com.autenticacion.login.exception;

public class CredencialesIncorrectas extends RuntimeException {

    public CredencialesIncorrectas (String mensaje){
        super(mensaje);
    }

}
