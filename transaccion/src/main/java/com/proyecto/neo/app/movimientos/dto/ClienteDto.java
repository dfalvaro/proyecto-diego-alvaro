package com.proyecto.neo.app.movimientos.dto;

import lombok.Data;

@Data
public class ClienteDto {

    private Long clienteid;
    private String nombre;
    private String identificacion;
    private Boolean estado;
    private String genero;
    private Long edad;
    private String direccion;
    private String telefono;
    private String contrasenia;
    private Long id;

}
