package com.credusan.captaciones.infraestructura.controladores;

import com.credusan.captaciones.aplicacion.ServicioCrearCaptacionExtracto;
import com.credusan.captaciones.dominio.modelos.CaptacionExtracto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/extractocaptacion")
public class ControladorCrearCaptacionExtracto {

    ServicioCrearCaptacionExtracto servicio;

    public ControladorCrearCaptacionExtracto(ServicioCrearCaptacionExtracto servicio) {
        this.servicio = servicio;
    }

    @PostMapping
    public ResponseEntity<CaptacionExtracto> create(@RequestBody CaptacionExtracto captacionExtracto) throws Exception {
        CaptacionExtracto captacionExtractoResponse = servicio.create(captacionExtracto);
        return new ResponseEntity<>(captacionExtractoResponse, HttpStatus.CREATED);
    }

}
