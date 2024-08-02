package com.proyecto.neo.app.movimientos.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.proyecto.neo.app.movimientos.dto.ClienteDto;
import com.proyecto.neo.app.movimientos.entities.Cuenta;
import com.proyecto.neo.app.movimientos.repositories.CuentaRepository;

@Service
public class CuentaServiceImpl implements CuentaService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CuentaRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<Cuenta> findAll() {
        return (List<Cuenta>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cuenta> findByNumeroCuenta(String numeroCuenta) {
        return repository.findByNumeroCuenta(numeroCuenta);
    }

    @Override
    @Transactional
    public Cuenta save(Cuenta cuenta) {
        return repository.save(cuenta);
    }

    @Override
    public Cuenta create(Long clienteId, String tipoCuenta, BigDecimal saldoInicial, String numeroCuenta) {
        // Consultar el microservicio de Clientes
        ResponseEntity<ClienteDto> response = restTemplate.getForEntity(
            "http://localhost:8080/api/clientes/" + clienteId, ClienteDto.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                
                // Crear y guardar la cuenta en la base de datos
                Cuenta cuenta = new Cuenta();

                if(numeroCuenta != null && !numeroCuenta.isEmpty()){
                    cuenta.setNumeroCuenta(numeroCuenta);
                }else{
                    cuenta.setNumeroCuenta(generarNumeroCuentaUnico());
                }
                cuenta.setTipoCuenta(tipoCuenta);
                cuenta.setSaldoInicial(saldoInicial);
                cuenta.setEstado(Boolean.TRUE);
                cuenta.setClienteId(clienteId); 
                
                return repository.save(cuenta);
            } else {
                throw new RuntimeException("Cliente no encontrado o error en la comunicación");
            }
    }

    @Override
    @Transactional
    public Optional<Cuenta> update(String numeroCuenta, Cuenta cuenta) {
        Optional<Cuenta> cuentaOptional = repository.findByNumeroCuenta(numeroCuenta);
        if (cuentaOptional.isPresent()) {
            Cuenta cuentaDb = cuentaOptional.orElseThrow();

            cuentaDb.setTipoCuenta(cuenta.getTipoCuenta());
            cuentaDb.setSaldoInicial(cuenta.getSaldoInicial());
            cuentaDb.setEstado(cuenta.getEstado());

            return Optional.of(repository.save(cuentaDb));

        }
        return cuentaOptional;
    }

    @Transactional
    @Override
    public Optional<Cuenta> delete(String id) {
        Optional<Cuenta> cuentaOptional = repository.findByNumeroCuenta(id);
        cuentaOptional.ifPresent(cuentaDb -> {
            repository.delete(cuentaDb);
        });
        return cuentaOptional;
    }

    @Transactional
    @Override
    public List<Cuenta> findByClienteId(Long clienteId) {
        return repository.findByClienteId(clienteId);
    }

    @Transactional
    @Override
    public void deleteByClienteId(Long clienteId) {
        List<Cuenta> cuentas = repository.findByClienteId(clienteId);
        cuentas.forEach(cuenta -> repository.delete(cuenta));
    }


    private String generarNumeroCuentaUnico() {
        SecureRandom random = new SecureRandom();
        StringBuilder numeroCuenta = new StringBuilder(6);
    
        for (int i = 0; i < 6; i++) {
            numeroCuenta.append(random.nextInt(10)); // Genera un dígito del 0 al 9
        }
    
        return numeroCuenta.toString();
    }

}
