package com.proyecto.neo.app.movimientos.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class EstadoCuentaDto {
    private Long clienteId;
    private String clienteNombre;
    private List<CuentaDto> cuentas;

    @Data
    public static class CuentaDto {
        private String numeroCuenta;
        private String tipoCuenta;
        private BigDecimal saldoInicial;
        private List<MovimientoDto> movimientos;
    }

    @Data
    public static class MovimientoDto {
        private Date fecha;
        private String tipoMovimiento;
        private BigDecimal valor;
        private BigDecimal saldo;
        
    }

}
