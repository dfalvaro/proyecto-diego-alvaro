package com.proyecto.neo.app.movimientos.controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
import com.proyecto.neo.app.movimientos.exceptions.ErrorResponse;
import com.proyecto.neo.app.movimientos.exceptions.SaldoInsuficienteException;
import com.proyecto.neo.app.movimientos.services.MovimientoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService service;

    /**
     * Maneja las solicitudes GET para obtener una lista de todos los movimientos.
     * 
     * @return un ResponseEntity que contiene la lista de todos los movimientos.
     */
    @GetMapping
    public ResponseEntity<List<Movimiento>> getAllMovements() {
        List<Movimiento> movimientos = service.findAll();
        return ResponseEntity.ok(movimientos);
    }

    /**
     * Maneja las solicitudes GET para obtener un movimiento por su identificador
     * único.
     * 
     * @param id el identificador único del movimiento.
     * @return un ResponseEntity que contiene el movimiento si se encuentra, o un
     *         estado 404 si no se encuentra.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getMovementById(@PathVariable Long id) {
        Optional<Movimiento> movimientoOptional = service.findById(id);
        return movimientoOptional
                .map(movimiento -> ResponseEntity.ok(movimiento))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Maneja las solicitudes POST para registrar un nuevo movimiento.
     * 
     * @param numeroCuenta   el número de cuenta asociado al movimiento.
     * @param tipoMovimiento el tipo de movimiento (ej. depósito, retiro).
     * @param valor          el valor del movimiento.
     * @return un ResponseEntity que contiene el movimiento registrado.
     */
    @PostMapping("/registrar")
    public ResponseEntity<Movimiento> registerMovement(@RequestParam String numeroCuenta,
            @RequestParam String tipoMovimiento,
            @RequestParam BigDecimal valor) {
        Movimiento movimiento = service.registerMovement(numeroCuenta, tipoMovimiento, valor);
        return ResponseEntity.ok(movimiento);
    }

     /**
     * Maneja las solicitudes POST para crear un nuevo movimiento.
     * 
     * @param movimiento la entidad movimiento a crear.
     * @param result el BindingResult para capturar errores de validación.
     * @return un ResponseEntity que contiene el movimiento creado, o un estado 400 si la validación falla.
     */
    @PostMapping
    public ResponseEntity<?> createMovement(@Valid @RequestBody Movimiento movimiento, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(handleValidationErrors(result));
        }
        Movimiento savedMovimiento = service.save(movimiento);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMovimiento);
    }

    /**
     * Maneja las solicitudes PUT para actualizar un movimiento existente por su identificador único.
     * 
     * @param movimiento la entidad movimiento con información actualizada.
     * @param result el BindingResult para capturar errores de validación.
     * @param id el identificador único del movimiento a actualizar.
     * @return un ResponseEntity que contiene el movimiento actualizado si se encuentra y se actualiza, o un estado 404 si no se encuentra.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMovement(@Valid @RequestBody Movimiento movimiento, BindingResult result, @PathVariable Long id) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(handleValidationErrors(result));
        }
        Optional<Movimiento> movimientoOptional = service.update(id, movimiento);
        return movimientoOptional
                .map(updatedMovimiento -> ResponseEntity.ok(updatedMovimiento))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Maneja las solicitudes DELETE para eliminar un movimiento por su identificador único.
     * 
     * @param id el identificador único del movimiento a eliminar.
     * @return un ResponseEntity que contiene el movimiento eliminado si se encuentra y se elimina, o un estado 404 si no se encuentra.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovement(@PathVariable Long id) {
        Optional<Movimiento> movimientoOptional = service.delete(id);
        if (movimientoOptional.isPresent()) {
            return ResponseEntity.ok(movimientoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

   /**
     * Maneja las excepciones de saldo insuficiente.
     * 
     * @param ex la excepción de saldo insuficiente.
     * @return un ResponseEntity que contiene la respuesta de error para saldo insuficiente.
     */
    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<String> handleSaldoInsuficienteException(SaldoInsuficienteException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Maneja las excepciones de validación.
     * 
     * @param ex la excepción de validación.
     * @return un ResponseEntity que contiene la respuesta de error de validación.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ErrorResponse errorResponse = handleValidationErrors(ex.getBindingResult());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * Maneja los errores de validación y crea una respuesta de error estructurada.
     * 
     * @param result el BindingResult que contiene los errores de validación.
     * @return una instancia de ErrorResponse que contiene los errores de validación.
     */
    @SuppressWarnings("null")
    private ErrorResponse handleValidationErrors(BindingResult result) {
        List<ErrorResponse.FieldError> fieldErrors = result.getFieldErrors().stream()
                .map(fieldError -> new ErrorResponse.FieldError(
                        fieldError.getField(),
                        fieldError.getRejectedValue() != null ? fieldError.getRejectedValue().toString() : null,
                        fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ErrorResponse("Error de validación", fieldErrors);
    }

}
