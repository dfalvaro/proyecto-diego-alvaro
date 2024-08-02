package com.proyecto.neo.app.general.services;

import java.util.List;
import java.util.Optional;

import com.proyecto.neo.app.general.entities.Cliente;

public interface ClienteService {

    /**
     * Obtiene una lista de todos los clientes.
     * 
     * @return una lista de todos los clientes.
     */
    List<Cliente> findAll();

    /**
     * Obtiene un cliente por su identificador único.
     * 
     * @param id el identificador único del cliente.
     * @return un Optional que contiene el cliente si se encuentra, o vacío si no se
     *         encuentra.
     */
    Optional<Cliente> findById(Long id);

    /**
     * Guarda un nuevo cliente o actualiza uno existente.
     * 
     * @param cliente la entidad cliente a guardar o actualizar.
     * @return la entidad cliente guardada o actualizada.
     */
    Cliente save(Cliente cliente);

    /**
     * Actualiza un cliente existente con nueva información.
     * 
     * @param id      el identificador único del cliente a actualizar.
     * @param cliente la entidad cliente con la información actualizada.
     * @return un Optional que contiene el cliente actualizado si se encuentra y se
     *         actualiza, o vacío si no se encuentra.
     */
    Optional<Cliente> update(Long id, Cliente cliente);

    /**
     * Elimina un cliente por su identificador único.
     * 
     * @param id el identificador único del cliente a eliminar.
     * @return un Optional que contiene el cliente eliminado si se encuentra y se
     *         elimina, o vacío si no se encuentra.
     */
    Optional<Cliente> delete(Long id);

}
