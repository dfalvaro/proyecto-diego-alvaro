package com.proyecto.neo.app.movimientos.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class EstadoCuentaDto {

    private Long clienteId;
    private String clienteNombre;
    private List<CuentaDto> cuentas;

    public static class CuentaDto {
        private String numeroCuenta;
        private String tipoCuenta;
        private BigDecimal saldoInicial;
        private List<MovimientoDto> movimientos;
       
        
        public String getTipoCuenta() {
            return tipoCuenta;
        }
        public void setTipoCuenta(String tipoCuenta) {
            this.tipoCuenta = tipoCuenta;
        }
        public BigDecimal getSaldoInicial() {
            return saldoInicial;
        }
        public void setSaldoInicial(BigDecimal saldoInicial) {
            this.saldoInicial = saldoInicial;
        }
        public List<MovimientoDto> getMovimientos() {
            return movimientos;
        }
        public void setMovimientos(List<MovimientoDto> movimientos) {
            this.movimientos = movimientos;
        }
        public String getNumeroCuenta() {
            return numeroCuenta;
        }
        public void setNumeroCuenta(String numeroCuenta) {
            this.numeroCuenta = numeroCuenta;
        }

        
    }

    public static class MovimientoDto {
        private Date fecha;
        private String tipoMovimiento;
        private BigDecimal valor;
        private BigDecimal saldo;
        public Date getFecha() {
            return fecha;
        }
        public void setFecha(Date fecha) {
            this.fecha = fecha;
        }
        public String getTipoMovimiento() {
            return tipoMovimiento;
        }
        public void setTipoMovimiento(String tipoMovimiento) {
            this.tipoMovimiento = tipoMovimiento;
        }
        public BigDecimal getValor() {
            return valor;
        }
        public void setValor(BigDecimal valor) {
            this.valor = valor;
        }
        public BigDecimal getSaldo() {
            return saldo;
        }
        public void setSaldo(BigDecimal saldo) {
            this.saldo = saldo;
        }

        
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public List<CuentaDto> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<CuentaDto> cuentas) {
        this.cuentas = cuentas;
    }


}
