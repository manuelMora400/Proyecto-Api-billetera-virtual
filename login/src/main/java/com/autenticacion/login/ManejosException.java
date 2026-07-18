package com.autenticacion.login;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.autenticacion.login.dto.ErrorDTO;
import com.autenticacion.login.exception.CredencialesIncorrectas;
import com.autenticacion.login.exception.NoExisteID;
import com.autenticacion.login.exception.RutNoExistente;
import com.autenticacion.login.exception.RutYaExiste;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ManejosException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> manejosErroresValidacion(
        MethodArgumentNotValidException ex,
        HttpServletRequest request){

        Map<String, String> error = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
            errores -> {error.put(errores.getField(), errores.getDefaultMessage());
        });
        ErrorDTO errorDTO = new ErrorDTO(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            ex.getMessage(),
            error,
            request.getRequestURI());
        return ResponseEntity.badRequest().body(errorDTO);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDTO> manejarErroresBaseDatos(
        DataIntegrityViolationException ex,
        HttpServletRequest request){

            ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                null,
                request.getRequestURI());
        return ResponseEntity.badRequest().body(errorDTO);
        }


    @ExceptionHandler(NoExisteID.class)
    public ResponseEntity<ErrorDTO> manejoErroresNotFoundPorID(
        NoExisteID ex,
        HttpServletRequest request){

            ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                null,
                request.getRequestURI()
            );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
        }

    @ExceptionHandler(RutYaExiste.class)
    public ResponseEntity<ErrorDTO> manejoRutExistente(
        RutYaExiste ex,
        HttpServletRequest request){

            ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                null,
                request.getRequestURI()
            );
        return ResponseEntity.badRequest().body(errorDTO);
    }

    @ExceptionHandler(RutNoExistente.class)
    public ResponseEntity<ErrorDTO> manejosNotFoundRut(
        RutNoExistente ex,
        HttpServletRequest request){

          ErrorDTO errorDTO = new ErrorDTO(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage(),
            null,
            request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }

    @ExceptionHandler(CredencialesIncorrectas.class)
    public ResponseEntity<ErrorDTO> manejosErrorCredenciales(
        CredencialesIncorrectas ex,
        HttpServletRequest request){

            ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(), // status 401
                ex.getMessage(),
                null,
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDTO);
    }
}
