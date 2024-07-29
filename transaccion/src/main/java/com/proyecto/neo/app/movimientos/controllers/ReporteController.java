package com.proyecto.neo.app.movimientos.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.neo.app.movimientos.dto.EstadoCuentaDto;
import com.proyecto.neo.app.movimientos.services.ReporteService;

@RestController
@RequestMapping("/api")
public class ReporteController {

     @Autowired
    private ReporteService reporteService;

    @GetMapping("/reportes")
    public ResponseEntity<EstadoCuentaDto> generarReporte(
            @RequestParam Long clienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaFin) {

        EstadoCuentaDto reporte = reporteService.generarReporte(clienteId, fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }

}
