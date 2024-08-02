package com.proyecto.neo.app.movimientos.entities;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
@Table(name = "cuentas")
@Data
public class Cuenta {
    @Id
    @Column(name = "numero_cuenta")
    private String numeroCuenta;

    @Column(name = "tipo_cuenta")
    @NotEmpty(message = "El tipo de cuenta no puede estar vacío")
    @Pattern(regexp = "^(Ahorro|Corriente)$", message = "El tipo de cuenta debe ser Ahorro o Corriente")
    private String tipoCuenta;

    @Column(name = "saldo_inicial")
    @NotNull(message = "El saldo inicial no puede ser nulo")
    private BigDecimal saldoInicial;

    private Boolean estado;

    @NotNull(message = "El ID del cliente no puede ser nulo")
    private Long clienteId;

    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Movimiento> movimientos;

}
