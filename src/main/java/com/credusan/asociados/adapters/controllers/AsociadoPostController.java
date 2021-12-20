package com.credusan.asociados.adapters.controllers;

import com.credusan.asociados.domain.models.Asociado;
import com.credusan.asociados.domain.services.AsociadoCreatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/asociados")
public class AsociadoPostController {

    private AsociadoCreatorService service;

    public AsociadoPostController(AsociadoCreatorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Asociado> create(@Valid @RequestBody Asociado tAsociadoRequest) throws Exception {
        Asociado tAsociadoResponse = service.create(tAsociadoRequest);
        return new ResponseEntity<>(tAsociadoResponse, HttpStatus.CREATED);
    }

}
