package com.autenticacion.login.service;

import java.util.Optional;

import com.autenticacion.login.model.Role; 

import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.autenticacion.login.exception.NoExisteID;
import com.autenticacion.login.exception.RutNoExistente;
import com.autenticacion.login.exception.RutYaExiste;
import com.autenticacion.login.model.Usuarios;
import com.autenticacion.login.repository.LoginRepository;
import com.autenticacion.login.security.JwtService;

@Service
public class AuthService {

    private final LoginRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(LoginRepository repository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
         JwtService jwtService){
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
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
        nuevoUsuarios.setCuentaBloqueada(false); // la cuenta podra ser operada 
        return repository.save(nuevoUsuarios);
     
    }

    public String login(String rut, String password){
        
        if(rut.length() > 13 || rut == null){
            throw new IllegalArgumentException("El tamaño del rut no puede ser mas de 12 caracteres y rut nulos no son validos");
        } else if (password == null){
            throw new IllegalArgumentException("La contraseña no puede estar vacia");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(rut, password));

        Optional<Usuarios> usuario = repository.findByRut(rut); // ¿Rut existe en la base de datos?
        if(usuario.isEmpty()){
            throw new RutNoExistente("El Usuario no se encuentra registrado en el sistema");
        }

        return jwtService.generateToken(usuario.get());

    }

    public void eliminarCuenta(Long id){
       if(id == null){
            throw new IllegalArgumentException("El id no puede ser nulo");
       }
       if(repository.existsById(id)){
            repository.deleteById(id);
       } else{
            throw new NoExisteID("El Id cuenta que desea eliminar no existe: " + id);
       }
    }
}
