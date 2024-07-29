package com.proyecto.neo.app.movimientos.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.proyecto.neo.app.movimientos.entities.Cuenta;

public interface CuentaRepository extends CrudRepository<Cuenta,String>{

     List<Cuenta> findByClienteId(Long clienteId);

     Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);

}
