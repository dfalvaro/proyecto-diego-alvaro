package com.proyecto.neo.app.general.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name = "clientes")
@Data
@PrimaryKeyJoinColumn(name = "id")
public class Cliente extends Persona{
    
    private String contrasenia;
    private Boolean estado;



}
