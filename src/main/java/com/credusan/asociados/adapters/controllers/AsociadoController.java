package com.credusan.asociados.adapters.controllers;

import com.credusan.asociados.domain.models.Asociado;
import com.credusan.asociados.domain.ports.input.AsociadoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/asociados")
public class AsociadoController {

    private AsociadoService service;

    public AsociadoController(AsociadoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Asociado> create(@Valid @RequestBody Asociado tAsociadoRequest) throws Exception {
        Asociado tAsociadoResponse = service.create(tAsociadoRequest);
        return new ResponseEntity<>(tAsociadoResponse, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Asociado> update(@RequestBody Asociado tAsociadoRequest) throws Exception {
        Asociado tAsociadoResponse = service.update(tAsociadoRequest.getIdAsociado(), tAsociadoRequest);
        return new ResponseEntity<>(tAsociadoResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<Asociado>> findAll(Pageable page) throws Exception {
        Page<Asociado> lista = service.getAll(page);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Asociado> findById(@PathVariable("id") Integer idAsociado) throws Exception {
        Asociado asociado = service.getById(idAsociado);
        return new ResponseEntity<>(asociado, HttpStatus.OK);
    }

    
/*
    @GetMapping("/paginable")
    public ResponseEntity<Page<Asociado>> listarPaginable(Pageable page) {
        Page<PacienteDTO> pacientes = this.service.listarPaginable(page).map(p -> mapper.map(p, PacienteDTO.class));
        return new ResponseEntity<>(pacientes, HttpStatus.OK);
    }
*/

}
