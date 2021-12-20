package com.credusan.asociados.adapters.controllers;

import com.credusan.asociados.domain.models.TipoDocumento;
import com.credusan.asociados.domain.services.TipoDocumentoFinderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tiposdocumento")
public class TipoDocumentoGetController {

    TipoDocumentoFinderService service;

    public TipoDocumentoGetController(TipoDocumentoFinderService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TipoDocumento>> findAll() throws Exception {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }


}
