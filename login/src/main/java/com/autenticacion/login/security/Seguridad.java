package com.autenticacion.login.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.autenticacion.login.model.Usuarios;
import com.autenticacion.login.repository.LoginRepository;

@Configuration
public class Seguridad {

    @Bean
    //Permito encriptar la contraseña
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    //Dejar los metodos hhtp publicos (solo mientras lo probamos)
    public SecurityFilterChain filterChain (HttpSecurity http){
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService(LoginRepository repository){
        return rut -> {
            Usuarios usuario = repository.findByRut(rut).orElseThrow(() -> new UsernameNotFoundException("usuario no encontrado"));
        return org.springframework.security.core.userdetails.User
            .withUsername(usuario.getRut())
            .password(usuario.getPassword())
            .roles("USER")
            .build();
        };
    }

}
