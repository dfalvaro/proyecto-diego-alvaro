package com.proyecto.neo.app.movimientos.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.proyecto.neo.app.movimientos.entities.Movimiento;

public interface MovimientoService {

    /**
     * Obtiene una lista de todos los movimientos.
     * 
     * @return una lista de todos los movimientos.
     */
    List<Movimiento> findAll();

    /**
     * Obtiene un movimiento por su identificador único.
     * 
     * @param id el identificador único del movimiento.
     * @return un Optional que contiene el movimiento si se encuentra, o vacío si no
     *         se
     *         encuentra.
     */
    Optional<Movimiento> findById(Long id);

    /**
     * Registra un nuevo movimiento en una cuenta.
     * 
     * @param numeroCuenta   el número de cuenta en la que se realizará el
     *                       movimiento.
     * @param tipoMovimiento el tipo de movimiento (por ejemplo, "depósito" o
     *                       "retiro").
     * @param valorDecimal   el valor del movimiento.
     * @return la entidad movimiento registrada.
     */
    Movimiento registerMovement(String numeroCuenta, String tipoMovimiento, BigDecimal valorDecimal);

    /**
     * Guarda un nuevo movimiento o actualiza uno existente.
     * 
     * @param movimiento la entidad movimiento a guardar o actualizar.
     * @return la entidad movimiento guardada o actualizada.
     */
    Movimiento save(Movimiento movimiento);

    /**
     * Actualiza un movimiento existente con nueva información.
     * 
     * @param id         el identificador único del movimiento a actualizar.
     * @param movimiento la entidad movimiento con la información actualizada.
     * @return un Optional que contiene el movimiento actualizado si se encuentra y
     *         se
     *         actualiza, o vacío si no se encuentra.
     */
    Optional<Movimiento> update(Long id, Movimiento movimiento);

    /**
     * Elimina un movimiento por su identificador único.
     * 
     * @param id el identificador único del movimiento a eliminar.
     * @return un Optional que contiene el movimiento eliminado si se encuentra y se
     *         elimina, o vacío si no se encuentra.
     */
    Optional<Movimiento> delete(Long id);
}
