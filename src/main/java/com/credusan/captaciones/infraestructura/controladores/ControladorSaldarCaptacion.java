package com.credusan.captaciones.infraestructura.controladores;

import com.credusan.captaciones.aplicacion.ServicioSaldarCaptacion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/captaciones")
public class ControladorSaldarCaptacion {

    ServicioSaldarCaptacion servicio;

    public ControladorSaldarCaptacion(ServicioSaldarCaptacion servicio) {
        this.servicio = servicio;
    }

    @PutMapping("/saldar/{id}")
    public ResponseEntity<Boolean> saldar(@PathVariable("id") Integer idCaptacion) throws Exception {
        Boolean respuesta = servicio.saldar(idCaptacion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

}
