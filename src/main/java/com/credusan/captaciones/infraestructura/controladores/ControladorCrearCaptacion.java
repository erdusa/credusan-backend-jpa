package com.credusan.captaciones.infraestructura.controladores;

import com.credusan.captaciones.aplicacion.ServicioCrearCaptacion;
import com.credusan.captaciones.dominio.modelos.Captacion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/captaciones")
public class ControladorCrearCaptacion {

    ServicioCrearCaptacion servicio;

    public ControladorCrearCaptacion(ServicioCrearCaptacion servicio) {
        this.servicio = servicio;
    }

    @PostMapping
    public ResponseEntity<Captacion> create(@RequestBody Captacion captacion) throws Exception {
        Captacion captacionResponse = servicio.create(captacion);
        return new ResponseEntity<>(captacionResponse, HttpStatus.CREATED);
    }

}
