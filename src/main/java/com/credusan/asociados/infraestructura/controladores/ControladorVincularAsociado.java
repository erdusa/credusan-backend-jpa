package com.credusan.asociados.infraestructura.controladores;

import com.credusan.asociados.aplicacion.ServicioVincularAsociado;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/asociados/vincular")
public class ControladorVincularAsociado {

    private final ServicioVincularAsociado service;

    public ControladorVincularAsociado(ServicioVincularAsociado service) {
        this.service = service;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer idAsociado) throws Exception {
        Boolean resultado = service.vincular(idAsociado);
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

}
