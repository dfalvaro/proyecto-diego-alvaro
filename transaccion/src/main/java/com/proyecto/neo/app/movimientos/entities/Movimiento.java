package com.proyecto.neo.app.movimientos.entities;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "movimientos")
@Data
public class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message = "La fecha del movimiento no puede ser nula")
    private Date fecha;
    
    @Column(name = "tipo_movimiento")
    @NotEmpty(message = "El tipo de movimiento no puede estar vacío")
    private String tipoMovimiento;

    @NotNull(message = "El valor del movimiento no puede ser nulo")
    private BigDecimal valor;

    @NotNull(message = "El saldo después del movimiento no puede ser nulo")
    private BigDecimal saldo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "numero_cuenta", nullable = false)
    private Cuenta cuenta;
    
}
