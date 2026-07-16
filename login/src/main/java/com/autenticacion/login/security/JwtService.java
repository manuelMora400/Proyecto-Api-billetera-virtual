package com.autenticacion.login.security;


import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;


@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long    jwtExpiration;

   
    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
        .setSubject(userDetails.getUsername()) // guarda el rut del usuario
        .setIssuedAt(new Date(System.currentTimeMillis())) // fecha y hora actual
        .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration )) // vencimiento
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact(); // convertimos todo en un string
    }

    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
