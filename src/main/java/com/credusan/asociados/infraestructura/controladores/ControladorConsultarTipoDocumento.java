package com.credusan.asociados.infraestructura.controladores;

import com.credusan.asociados.dominio.modelos.TipoDocumento;
import com.credusan.asociados.aplicacion.ServicioConsultarTipoDocumento;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tiposdocumento")
public class ControladorConsultarTipoDocumento {

    ServicioConsultarTipoDocumento service;

    public ControladorConsultarTipoDocumento(ServicioConsultarTipoDocumento service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TipoDocumento>> findAll() throws Exception {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }


}
