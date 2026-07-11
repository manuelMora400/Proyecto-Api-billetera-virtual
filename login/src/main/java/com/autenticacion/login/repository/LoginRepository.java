package com.autenticacion.login.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autenticacion.login.model.usuarios;

public interface LoginRepository extends JpaRepository<usuarios, Long>{
    
    // Buscaremos por rut identificador unico
    Optional<usuarios> findByRut(String rut); 

}
