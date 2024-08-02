package com.proyecto.neo.app.movimientos.controllers;

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
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.neo.app.movimientos.entities.Cuenta;
import com.proyecto.neo.app.movimientos.exceptions.ErrorResponse;
import com.proyecto.neo.app.movimientos.exceptions.ResourceNotFoundException;
import com.proyecto.neo.app.movimientos.services.CuentaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {
    @Autowired
    private CuentaService service;

    /**
     * Maneja las solicitudes GET para obtener una lista de todas las cuentas.
     * 
     * @return un ResponseEntity que contiene la lista de todas las cuentas.
     */
    @GetMapping
    public ResponseEntity<List<Cuenta>> getAllAccounts() {
        List<Cuenta> cuentas = service.findAll();
        return ResponseEntity.ok(cuentas);
    }

    /**
     * Maneja las solicitudes GET para obtener una cuenta por su número de cuenta.
     * 
     * @param numeroCuenta el número de cuenta.
     * @return un ResponseEntity que contiene la cuenta si se encuentra, o un
     *         estado 404 si no se encuentra.
     */
    @GetMapping("/{numeroCuenta}")
    public ResponseEntity<?> getAccountByNumber(@PathVariable String numeroCuenta) {
        Optional<Cuenta> cuentaOptional = service.findByNumeroCuenta(numeroCuenta);
        return cuentaOptional
                .map(cuenta -> ResponseEntity.ok(cuenta))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Maneja las solicitudes POST para crear una nueva cuenta.
     * 
     * @param cuenta la entidad cuenta a crear.
     * @param result el BindingResult para capturar errores de validación.
     * @return un ResponseEntity que contiene la cuenta creada, o un estado 400 si
     *         la validación falla.
     */
    @PostMapping
    public ResponseEntity<?> createAccount(@Valid @RequestBody Cuenta cuenta, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(handleValidationErrors(result));
        }
        Cuenta savedCuenta = service.save(cuenta);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCuenta);
    }

    /**
     * Maneja las solicitudes POST para crear una cuenta con información específica.
     * 
     * @param request la entidad cuenta con información para crear una nueva cuenta.
     * @return un ResponseEntity que contiene la nueva cuenta creada, o un estado
     *         400 si
     *         ocurre un error.
     */
    @PostMapping("/crear")
    public ResponseEntity<?> createSpecificAccount(@Valid @RequestBody Cuenta cuenta, BindingResult result) {
        try {
            // Validar los errores de BindingResult antes de proceder
            if (result.hasErrors()) {
                return ResponseEntity.badRequest().body(handleValidationErrors(result));
            }

            // Intentar crear una nueva cuenta
            Cuenta nuevaCuenta = service.create(
                    cuenta.getClienteId(),
                    cuenta.getTipoCuenta(),
                    cuenta.getSaldoInicial(),
                    cuenta.getNumeroCuenta());
            return ResponseEntity.ok(nuevaCuenta);

        } catch (RuntimeException e) {
            // Retornar el mensaje de la excepción en la respuesta
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /**
     * Maneja las solicitudes PUT para actualizar una cuenta existente por su número
     * de cuenta.
     * 
     * @param cuenta       la entidad cuenta con información actualizada.
     * @param result       el BindingResult para capturar errores de validación.
     * @param numeroCuenta el número de cuenta a actualizar.
     * @return un ResponseEntity que contiene la cuenta actualizada si se encuentra
     *         y se actualiza, o un estado 404 si no se encuentra.
     */
    @PutMapping("/{numeroCuenta}")
    public ResponseEntity<?> updateAccount(@Valid @RequestBody Cuenta cuenta, BindingResult result,
            @PathVariable String numeroCuenta) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(handleValidationErrors(result));
        }
        Optional<Cuenta> cuentaOptional = service.update(numeroCuenta, cuenta);
        return cuentaOptional
                .map(updatedCuenta -> ResponseEntity.ok(updatedCuenta))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Maneja las solicitudes DELETE para eliminar una cuenta por su número de
     * cuenta.
     * 
     * @param numeroCuenta el número de cuenta a eliminar.
     * @return un ResponseEntity que contiene la cuenta eliminada si se encuentra y
     *         se elimina, o un estado 404 si no se encuentra.
     */
    @DeleteMapping("/{numeroCuenta}")
    public ResponseEntity<?> deleteAccount(@PathVariable String numeroCuenta) {
        Optional<Cuenta> cuentaOptional = service.delete(numeroCuenta);
        if (cuentaOptional.isPresent()) {
            return ResponseEntity.ok(cuentaOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/cliente/{clienteId}")
    public ResponseEntity<?> deleteAccountsByClienteId(@PathVariable Long clienteId) {
        List<Cuenta> cuentas = service.findByClienteId(clienteId);
        if (cuentas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        cuentas.forEach(cuenta -> service.delete(cuenta.getNumeroCuenta()));
        return ResponseEntity.ok("Cuentas eliminadas");
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
     * Maneja las excepciones cuando un recurso no se encuentra.
     * 
     * @param ex la excepción de recurso no encontrado.
     * @return un ResponseEntity que contiene la respuesta de error para el recurso
     *         no encontrado.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * Maneja los errores de validación y crea una respuesta de error estructurada.
     * 
     * @param result el BindingResult que contiene los errores de validación.
     * @return una instancia de ErrorResponse que contiene los errores de
     *         validación.
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
