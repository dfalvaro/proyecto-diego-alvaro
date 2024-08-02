package com.proyecto.neo.app.general.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
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
import org.springframework.web.client.RestTemplate;

import com.proyecto.neo.app.general.entities.Cliente;
import com.proyecto.neo.app.general.exceptions.ErrorResponse;
import com.proyecto.neo.app.general.exceptions.ResourceNotFoundException;
import com.proyecto.neo.app.general.services.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

     @Autowired
    private RestTemplate restTemplate;

    // URL del microservicio de cuentas
    private final String cuentasServiceUrl = "http://localhost:8081/api/cuentas/cliente/";

    /**
     * Maneja las solicitudes GET para obtener una lista de todos los clientes.
     * 
     * @return un ResponseEntity que contiene la lista de todos los clientes.
     */
    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClients() {
        List<Cliente> clientes = service.findAll();
        return ResponseEntity.ok(clientes);
    }

    /**
     * Maneja las solicitudes GET para obtener un cliente por su identificador
     * único.
     * 
     * @param id el identificador único del cliente.
     * @return un ResponseEntity que contiene el cliente si se encuentra, o un
     *         estado 404 si no se encuentra.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getClientById(@PathVariable Long id) {
        Optional<Cliente> clienteOptional = service.findById(id);
        return clienteOptional
                .map(cliente -> ResponseEntity.ok(cliente))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Maneja las solicitudes POST para crear un nuevo cliente.
     * 
     * @param cliente la entidad cliente a crear.
     * @param result  el BindingResult para capturar errores de validación.
     * @return un ResponseEntity que contiene el cliente creado, o un estado 400 si
     *         la validación falla.
     */
    @PostMapping
    public ResponseEntity<?> createClient(@Valid @RequestBody Cliente cliente, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(handleValidationErrors(result));
        }
        Cliente savedCliente = service.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCliente);
    }

    /**
     * Maneja las solicitudes PUT para actualizar un cliente existente por su
     * identificador único.
     * 
     * @param cliente la entidad cliente con información actualizada.
     * @param result  el BindingResult para capturar errores de validación.
     * @param id      el identificador único del cliente a actualizar.
     * @return un ResponseEntity que contiene el cliente actualizado si se encuentra
     *         y se actualiza, o un estado 404 si no se encuentra.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateClient(@Valid @RequestBody Cliente cliente, BindingResult result,
            @PathVariable Long id) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(handleValidationErrors(result));
        }
        Optional<Cliente> clienteOptional = service.update(id, cliente);
        return clienteOptional
                .map(updatedCliente -> ResponseEntity.ok(updatedCliente))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Maneja las solicitudes DELETE para eliminar un cliente por su identificador
     * único.
     * 
     * @param id el identificador único del cliente a eliminar.
     * @return un ResponseEntity que contiene el cliente eliminado si se encuentra y
     *         se elimina, o un estado 404 si no se encuentra.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
       // Eliminar cuentas del cliente
        ResponseEntity<String> cuentasResponse = restTemplate.exchange(
                cuentasServiceUrl + id, 
                HttpMethod.DELETE, 
                null, 
                String.class
        );

        if (cuentasResponse.getStatusCode() == HttpStatus.OK) {
            Optional<Cliente> clienteOptional = service.delete(id);
            return clienteOptional
                    .map(cliente -> ResponseEntity.ok(cliente))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar las cuentas del cliente");
        }
    }

    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ErrorResponse errorResponse = handleValidationErrors(ex.getBindingResult());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @SuppressWarnings("null")
    private ErrorResponse handleValidationErrors(BindingResult result) {
        List<ErrorResponse.FieldError> fieldErrors = result.getFieldErrors().stream()
                .map(fieldError -> new ErrorResponse.FieldError(
                        fieldError.getField(),
                        fieldError.getRejectedValue() != null ? fieldError.getRejectedValue().toString() : null,
                        fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ErrorResponse("Validation failed", fieldErrors);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
