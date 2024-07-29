package com.proyecto.neo.app.movimientos.controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.neo.app.movimientos.entities.Movimiento;
import com.proyecto.neo.app.movimientos.exceptions.SaldoInsuficienteException;
import com.proyecto.neo.app.movimientos.services.MovimientoService;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService service;

    @GetMapping
    public List<Movimiento> list() {
        return service.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> view(@PathVariable Long id) {
        Optional<Movimiento> movimientoOptional = service.findById(id);
        if (movimientoOptional.isPresent()) {
            return ResponseEntity.ok(movimientoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/registrar")
    public ResponseEntity<Movimiento> registrarMovimiento(@RequestParam String numeroCuenta,
                                                          @RequestParam String tipoMovimiento,
                                                          @RequestParam BigDecimal valor) {
        Movimiento movimiento = service.registrarMovimiento(numeroCuenta, tipoMovimiento, valor);
        return ResponseEntity.ok(movimiento);
    }

    
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Movimiento movimiento, BindingResult result) {
               return ResponseEntity.status(HttpStatus.CREATED).body(service.save(movimiento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Movimiento movimiento, BindingResult result, @PathVariable Long id) {
       
        Optional<Movimiento> movimientoOptional = service.update(id, movimiento);
        if (movimientoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(movimientoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Movimiento> movimientoOptional = service.delete(id);
        if (movimientoOptional.isPresent()) {
            return ResponseEntity.ok(movimientoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    
     @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<String> handleSaldoInsuficienteException(SaldoInsuficienteException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}
