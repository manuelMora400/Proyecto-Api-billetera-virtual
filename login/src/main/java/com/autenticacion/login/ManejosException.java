package com.autenticacion.login;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.autenticacion.login.dto.ErrorDTO;
import com.autenticacion.login.exception.NoExisteID;
import com.autenticacion.login.exception.RutYaExiste;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ManejosException {

    @ExceptionHandler(NoExisteID.class)
    public ResponseEntity<ErrorDTO> manejoErroresNotFound(
        NoExisteID ex,
        HttpServletRequest request){

            ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                404,
                ex.getMessage(),
                null,
                request.getRequestURI()
            );
        return ResponseEntity.status(404).body(errorDTO);
        }
    @ExceptionHandler(RutYaExiste.class)
    public ResponseEntity<ErrorDTO> manejoRutExistente(
        RutYaExiste ex,
        HttpServletRequest request){

            ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                400, // dato invalido el usuario ingresa un rut que no le corresponde
                ex.getMessage(),
                null,
                request.getRequestURI()
            );
        return ResponseEntity.badRequest().body(errorDTO);
        }



}
