package com.proyecto.neo.app.general.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "personas")
@Entity
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private String genero;
    private Long edad;
    private String identificacion;
    private String direccion;
    private String telefono;
    
}
