package com.autenticacion.login.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.autenticacion.login.model.Usuarios;

@Repository
public interface LoginRepository extends JpaRepository<Usuarios, Long>{
    
    // Buscaremos por rut identificador unico
    Optional<Usuarios> findByRut(String rut); 

}
