package com.proyecto.neo.app.movimientos.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.proyecto.neo.app.movimientos.entities.Cuenta;

public interface CuentaService {

    /**
     * Obtiene una lista de todas las cuentas.
     * 
     * @return una lista de todas las cuentas.
     */
    List<Cuenta> findAll();

     /**
     * Obtiene una cuenta por su número de cuenta único.
     * 
     * @param numeroCuenta el número de cuenta único.
     * @return un Optional que contiene la cuenta si se encuentra, o vacío si no se
     *         encuentra.
     */
    Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);

    /**
     * Guarda una nueva cuenta o actualiza una existente.
     * 
     * @param cuenta la entidad cuenta a guardar o actualizar.
     * @return la entidad cuenta guardada o actualizada.
     */
    Cuenta save(Cuenta cuenta);

     /**
     * Crea una nueva cuenta bancaria con la información proporcionada.
     * 
     * @param clienteId    el identificador único del cliente asociado a la cuenta.
     * @param tipoCuenta   el tipo de cuenta a crear.
     * @param saldoInicial el saldo inicial de la cuenta.
     * @param numeroCuenta el número de cuenta.
     * @return la entidad cuenta creada.
     */
    Cuenta create(Long clienteId, String tipoCuenta, BigDecimal saldoInicial, String numeroCuenta);


    /**
     * Actualiza una cuenta existente con nueva información.
     * 
     * @param numeroCuenta el número de cuenta de la cuenta a actualizar.
     * @param cuenta       la entidad cuenta con la información actualizada.
     * @return un Optional que contiene la cuenta actualizada si se encuentra y se
     *         actualiza, o vacío si no se encuentra.
     */
    Optional<Cuenta> update(String numeroCuenta, Cuenta cuenta);


/**
     * Elimina una cuenta por su número de cuenta único.
     * 
     * @param numeroCuenta el número de cuenta de la cuenta a eliminar.
     * @return un Optional que contiene la cuenta eliminada si se encuentra y se
     *         elimina, o vacío si no se encuentra.
     */
    Optional<Cuenta> delete(String numeroCuenta);

    /**
     * 
     * @param clienteId
     * @return
     */
    List<Cuenta> findByClienteId(Long clienteId);

    /**
     * 
     * @param clienteId
     */
    void deleteByClienteId(Long clienteId);
}
