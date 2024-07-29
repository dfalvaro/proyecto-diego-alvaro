package com.proyecto.neo.app.movimientos.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.neo.app.movimientos.entities.Cuenta;
import com.proyecto.neo.app.movimientos.services.CuentaService;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {
    @Autowired
    private CuentaService service;

    @GetMapping
    public List<Cuenta> list() {
        return service.findAll();
    }
    
    @GetMapping("/{numeroCuenta}")
    public ResponseEntity<?> view(@PathVariable String numeroCuenta) {
        Optional<Cuenta> cuentaOptional = service.findByNumeroCuenta(numeroCuenta);
        if (cuentaOptional.isPresent()) {
            return ResponseEntity.ok(cuentaOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Cuenta cuenta, BindingResult result) {
       
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cuenta));
    }

    @PostMapping("/crear")
    public ResponseEntity<Cuenta> crearCuenta(@RequestBody Cuenta request) {
        try {
            Cuenta nuevaCuenta = service.create(
                request.getClienteId(),
                request.getTipoCuenta(),
                request.getSaldoInicial(),
                request.getNumeroCuenta()
            );
            return ResponseEntity.ok(nuevaCuenta);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @PutMapping("/{numeroCuenta}")
    public ResponseEntity<?> update(@RequestBody Cuenta cuenta, BindingResult result, @PathVariable String numeroCuenta) {
        
        Optional<Cuenta> cuentaOptional = service.update(numeroCuenta, cuenta);
        if (cuentaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(cuentaOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

   

    @DeleteMapping("/{numeroCuenta}")
    public ResponseEntity<?> delete(@PathVariable String numeroCuenta) {
        Optional<Cuenta> cuentaOptional = service.delete(numeroCuenta);
        if (cuentaOptional.isPresent()) {
            return ResponseEntity.ok(cuentaOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    

}
