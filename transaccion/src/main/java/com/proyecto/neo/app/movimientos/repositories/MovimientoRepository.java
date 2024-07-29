package com.proyecto.neo.app.movimientos.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.proyecto.neo.app.movimientos.entities.Movimiento;

public interface MovimientoRepository extends CrudRepository<Movimiento,Long>{
    List<Movimiento> findByCuenta_NumeroCuentaAndFechaBetween(String numeroCuenta, Date fechaInicio, Date fechaFin);
}
