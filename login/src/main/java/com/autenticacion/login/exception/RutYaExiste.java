package com.autenticacion.login.exception;

public class RutYaExiste extends RuntimeException{

    public RutYaExiste(String mensaje){
        super(mensaje);
    }
}
