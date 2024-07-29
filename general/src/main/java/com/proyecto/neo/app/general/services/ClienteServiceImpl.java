package com.proyecto.neo.app.general.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.neo.app.general.entities.Cliente;
import com.proyecto.neo.app.general.repositories.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService{

    @Autowired
    private ClienteRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<Cliente> findAll() {
        return (List<Cliente>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Cliente save(Cliente cliente) {
        return repository.save(cliente);
    }

    @Override
    @Transactional
    public Optional<Cliente> update(Long id, Cliente cliente) {
        Optional<Cliente> clienteOptional = repository.findById(id);
        if (clienteOptional.isPresent()) {
            Cliente clienteDb = clienteOptional.orElseThrow();
            
            clienteDb.setNombre(cliente.getNombre());
            clienteDb.setGenero(cliente.getGenero());
            clienteDb.setEdad(cliente.getEdad());
            clienteDb.setIdentificacion(cliente.getIdentificacion());
            clienteDb.setDireccion(cliente.getDireccion());
            clienteDb.setTelefono(cliente.getTelefono());
            clienteDb.setContrasenia(cliente.getContrasenia());
            clienteDb.setEstado(cliente.getEstado());

            return Optional.of(repository.save(clienteDb));
            
        }
        return clienteOptional;
    }

    @Transactional
    @Override
    public Optional<Cliente> delete(Long id) {
        Optional<Cliente> clienteOptional = repository.findById(id);
        clienteOptional.ifPresent(clienteDb -> {
            repository.delete(clienteDb);
        });
        return clienteOptional;
    }

}
