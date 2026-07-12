package com.autenticacion.login.service;

import java.util.Optional;

import com.autenticacion.login.model.Role; 


import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.autenticacion.login.exception.RutYaExiste;
import com.autenticacion.login.model.Usuarios;
import com.autenticacion.login.repository.LoginRepository;

@Service
public class AuthService {

    private final LoginRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(LoginRepository repository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager){
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public Usuarios crearUsuario (Usuarios usuarios){

        Optional<Usuarios> existe = repository.findByRut(usuarios.getRut());

        if(existe.isPresent()){
            throw new RutYaExiste("El rut ya esta registrado");
        }

        Usuarios nuevoUsuarios = new Usuarios();
        String passwordEncriptada = passwordEncoder.encode(usuarios.getPassword());

        nuevoUsuarios.setRut(usuarios.getRut());
        nuevoUsuarios.setPassword(passwordEncriptada);// encripto la contraseña
        nuevoUsuarios.setRole(Role.USER); // solo usuarios usaran este metodo
        nuevoUsuarios.setCuentaBloqueada(false);
        return repository.save(nuevoUsuarios);
     
    }
}
