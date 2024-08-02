package com.proyecto.neo.app.general.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "personas")
@Entity
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotEmpty(message = "El género no puede estar vacío")
    @Pattern(regexp = "^(Masculino|Femenino|Otro)$", message = "El género debe ser Masculino, Femenino o Otro")
    private String genero;

    @NotNull(message = "La edad no puede ser nula")
    private Long edad;

    @NotEmpty(message = "La identificación no puede estar vacía")
    private String identificacion;

    @NotEmpty(message = "La dirección no puede estar vacía")
    private String direccion;
    
    private String telefono;
    
}
