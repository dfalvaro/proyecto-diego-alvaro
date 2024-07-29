package com.proyecto.neo.app.movimientos.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.proyecto.neo.app.movimientos.entities.Cuenta;

public interface CuentaService {
    List<Cuenta> findAll(); 

    Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);

    Cuenta save(Cuenta cuenta);

    Cuenta create(Long clienteId, String tipoCuenta, BigDecimal saldoInicial, String numeroCuenta);

    Optional<Cuenta> update(String numeroCuenta, Cuenta cuenta);

    Optional<Cuenta> delete(String numeroCuenta);
}
