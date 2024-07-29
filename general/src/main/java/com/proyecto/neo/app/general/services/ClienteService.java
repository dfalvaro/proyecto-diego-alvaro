package com.proyecto.neo.app.general.services;


import java.util.List;
import java.util.Optional;

import com.proyecto.neo.app.general.entities.Cliente;

public interface ClienteService {

    List<Cliente> findAll(); 

    Optional<Cliente> findById(Long id);

    Cliente save(Cliente cliente);

    Optional<Cliente> update(Long id, Cliente cliente);

    Optional<Cliente> delete(Long id);

}
