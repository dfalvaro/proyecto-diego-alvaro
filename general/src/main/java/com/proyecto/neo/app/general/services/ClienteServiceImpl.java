package com.proyecto.neo.app.general.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.neo.app.general.entities.Cliente;
import com.proyecto.neo.app.general.exceptions.ResourceNotFoundException;
import com.proyecto.neo.app.general.repositories.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService{

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    @Override
    public List<Cliente> findAll() {
        return (List<Cliente>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> findById(Long id) {
        return Optional.ofNullable(repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id)));

    }

    @Override
    @Transactional
    public Cliente save(Cliente cliente) {
        cliente.setContrasenia(passwordEncoder.encode(cliente.getContrasenia()));
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

            if (cliente.getContrasenia() != null && !cliente.getContrasenia().isEmpty()) {
                clienteDb.setContrasenia(passwordEncoder.encode(cliente.getContrasenia()));
            }

            clienteDb.setEstado(cliente.getEstado());

            return Optional.of(repository.save(clienteDb));
            
        }
        return clienteOptional;
    }

    @Transactional
    @Override
    public Optional<Cliente> delete(Long id) {
        Optional<Cliente> clienteOptional = repository.findById(id);
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            repository.delete(cliente);
            return Optional.of(cliente);
        } else {
            throw new ResourceNotFoundException("Cliente no encontrado con id: " + id);
        }
    }

}
