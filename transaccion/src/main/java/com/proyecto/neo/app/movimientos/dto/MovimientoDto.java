package com.proyecto.neo.app.movimientos.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class MovimientoDto {

    private Long id;
    private Date fecha;
    private String tipoMovimiento;
    private BigDecimal valor;
    private BigDecimal saldo;
   
}
