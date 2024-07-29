package com.proyecto.neo.app.general;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.proyecto.neo.app.general.entities.Cliente;
import com.proyecto.neo.app.general.repositories.ClienteRepository;
import com.proyecto.neo.app.general.services.ClienteServiceImpl;

@ExtendWith(MockitoExtension.class) //JUnit debe usar soporte para Mockito y DI
public class ClienteTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @Test
    public void testGuardarCliente() {

        Cliente cliente = new Cliente();
        cliente.setNombre("Diego");
        cliente.setGenero("Alvaro");
        cliente.setEdad(32L);
        cliente.setIdentificacion("1718892746");
        cliente.setDireccion("Calle 13");
        cliente.setTelefono("0983101346");
        cliente.setContrasenia("1234");
        cliente.setEstado(Boolean.TRUE);

        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente clienteGuardado = clienteService.save(cliente);

        assertEquals(cliente.getNombre(), clienteGuardado.getNombre());
        assertEquals(cliente.getGenero(), clienteGuardado.getGenero());
        assertEquals(cliente.getEdad(), clienteGuardado.getEdad());
        assertEquals(cliente.getIdentificacion(), clienteGuardado.getIdentificacion());
        assertEquals(cliente.getDireccion(), clienteGuardado.getDireccion());
        assertEquals(cliente.getTelefono(), clienteGuardado.getTelefono());
        assertEquals(cliente.getContrasenia(), clienteGuardado.getContrasenia());
        assertEquals(cliente.getEstado(), clienteGuardado.getEstado());

        verify(clienteRepository, times(1)).save(cliente);
    }
}
