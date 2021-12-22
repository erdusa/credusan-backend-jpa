package com.credusan.asociados.adapters.controllers;

import com.credusan.asociados.domain.services.AsociadoRetiroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/asociados/retirar")
public class AsociadoRetiroController {

    private AsociadoRetiroService service;

    public AsociadoRetiroController(AsociadoRetiroService service) {
        this.service = service;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer idAsociado) {
        Boolean resultado = service.retirarAsociado(idAsociado);
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

}
