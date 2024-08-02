package com.proyecto.neo.app.general.entities;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Entity
@Table(name = "clientes")
@Data
@PrimaryKeyJoinColumn(name = "id")
public class Cliente extends Persona{
    
    @NotEmpty(message = "La contraseña no puede estar vacía")
    private String contrasenia;

    @NotNull(message = "El estado no puede ser nulo")
    private Boolean estado;

    public void setContrasenia(String contrasenia) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.contrasenia = encoder.encode(contrasenia);
    }


}
