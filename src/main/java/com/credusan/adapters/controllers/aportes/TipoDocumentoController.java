package com.credusan.adapters.controllers.aportes;

import com.credusan.domain.models.aportes.TipoDocumento;
import com.credusan.domain.ports.input.aportes.TipoDocumentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tiposdocumento")
public class TipoDocumentoController {

    TipoDocumentoService service;

    public TipoDocumentoController(TipoDocumentoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TipoDocumento>> findAll() throws Exception {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }


}
