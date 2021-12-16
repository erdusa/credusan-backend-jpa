package com.credusan.adapters.controllers.aportes;

import com.credusan.domain.models.aportes.Asociado;
import com.credusan.domain.ports.input.aportes.AsociadoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public ResponseEntity<List<Asociado>> findAll() throws Exception {
        List<Asociado> lista = service.getAll();
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
