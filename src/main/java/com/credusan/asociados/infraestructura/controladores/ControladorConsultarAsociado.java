package com.credusan.asociados.infraestructura.controladores;

import com.credusan.asociados.aplicacion.ServicioConsultarAsociado;
import com.credusan.asociados.dominio.modelos.Asociado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/asociados")
public class ControladorConsultarAsociado {

    private final ServicioConsultarAsociado service;

    public ControladorConsultarAsociado(ServicioConsultarAsociado service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<Asociado>> getAll(Pageable pageable, @RequestParam("soloActivos") boolean soloActivos) throws Exception {
        Page<Asociado> lista = service.getAll(pageable, soloActivos);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Asociado> getById(@PathVariable("id") Integer idAsociado) throws Exception {
        Asociado asociado = service.getById(idAsociado);
        return new ResponseEntity<>(asociado, HttpStatus.OK);
    }

    @GetMapping("/name/{names}")
    public ResponseEntity<List<Asociado>> getByNameOrSurnames(@PathVariable("names") String nombres) throws Exception {
        List<Asociado> asociado = service.getAllByNameOrSurnames(nombres);
        return new ResponseEntity<>(asociado, HttpStatus.OK);
    }

}
