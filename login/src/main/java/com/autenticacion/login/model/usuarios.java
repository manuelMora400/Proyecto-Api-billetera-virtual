package com.autenticacion.login.model;

import java.util.Collection;
import java.util.List;



import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "usuarios")
@AllArgsConstructor
@NoArgsConstructor
public class Usuarios implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Id autoincrementable
    private Long id;

    @NotBlank(message = "El Rut del usuario es obligatorio")
    @Column(nullable = false , length = 12, unique = true)
    private String rut;

    @NotBlank( message = "La contraseña es obligatoria para inicio de sesion")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //Evita que devuelva la contraseña en json
    private String password;

    @Enumerated(EnumType.STRING) // permitimos que la base de datos la guarde con su nombre
    @Column(nullable = false)
    private Role role;

    private boolean cuentaBloqueada;
//------------------------------------------------------------------------------------------------

    @Override
    public Collection <? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
    }

    @Override
    public String getUsername(){ // este metodo usara Spring security
        return rut;
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public boolean isAccountNonExpired(){  // permito que la cuenta nunca expire
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){ 
        return !cuentaBloqueada;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return true; // La password nunca va vencer
    }

    @Override
    public boolean isEnabled(){
        return true;
    }


    

}
