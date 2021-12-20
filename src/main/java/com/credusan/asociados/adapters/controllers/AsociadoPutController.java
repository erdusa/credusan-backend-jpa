package com.credusan.asociados.adapters.controllers;

import com.credusan.asociados.domain.models.Asociado;
import com.credusan.asociados.domain.services.AsociadoUpdaterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/asociados")
public class AsociadoPutController {

    private AsociadoUpdaterService service;

    public AsociadoPutController(AsociadoUpdaterService service) {
        this.service = service;
    }

    @PutMapping
    public ResponseEntity<Asociado> update(@RequestBody Asociado tAsociadoRequest) throws Exception {
        Asociado tAsociadoResponse = service.update(tAsociadoRequest.getIdAsociado(), tAsociadoRequest);
        return new ResponseEntity<>(tAsociadoResponse, HttpStatus.OK);
    }

}
