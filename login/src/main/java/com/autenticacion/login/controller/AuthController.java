package com.autenticacion.login.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autenticacion.login.model.Usuarios;
import com.autenticacion.login.service.AuthService;

import jakarta.validation.Valid;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/api/autenticacion")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service){
        this.service = service;
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearCuenta(@Valid @RequestBody Usuarios usuarios) {
        Usuarios usuarioCreado = service.crearUsuario(usuarios);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreado);
    }
    // Metodo actualizar pendiente //

   //  Metodo login pendiente //
    
   @DeleteMapping("/eliminar/{id}")
   public ResponseEntity<?> eliminarCuenta(@PathVariable long id){
        service.eliminarCuenta(id);
        return ResponseEntity.status(HttpStatus.OK).body("Cuenta eliminada con exito");
    }

}
