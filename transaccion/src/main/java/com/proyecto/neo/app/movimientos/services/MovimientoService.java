package com.proyecto.neo.app.movimientos.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.proyecto.neo.app.movimientos.entities.Movimiento;

public interface MovimientoService {

    List<Movimiento> findAll(); 

    Optional<Movimiento> findById(Long id);

    Movimiento registrarMovimiento(String numeroCuenta, String tipoMovimiento, BigDecimal valorDecimal);

    Movimiento save(Movimiento movimiento);

    Optional<Movimiento> update(Long id, Movimiento movimiento);

    Optional<Movimiento> delete(Long id);
}
