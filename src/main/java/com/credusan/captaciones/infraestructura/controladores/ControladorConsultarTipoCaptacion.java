package com.credusan.captaciones.infraestructura.controladores;

import com.credusan.captaciones.aplicacion.ServicioConsultarTipoCaptacion;
import com.credusan.captaciones.dominio.modelos.TipoCaptacion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tipos_captacion")
public class ControladorConsultarTipoCaptacion {

    ServicioConsultarTipoCaptacion servicio;

    public ControladorConsultarTipoCaptacion(ServicioConsultarTipoCaptacion servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public ResponseEntity<List<TipoCaptacion>> getAll() {
        return new ResponseEntity<>(servicio.getAll(), HttpStatus.OK);
    }


}
