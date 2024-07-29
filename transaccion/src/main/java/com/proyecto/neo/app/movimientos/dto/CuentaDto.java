package com.proyecto.neo.app.movimientos.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CuentaDto {
    
    private Long id;
    private Long numeroCuenta;
    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private Boolean estado;

}
