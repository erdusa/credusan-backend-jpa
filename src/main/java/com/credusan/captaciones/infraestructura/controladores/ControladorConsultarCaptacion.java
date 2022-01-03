package com.credusan.captaciones.infraestructura.controladores;

import com.credusan.captaciones.aplicacion.ServicioConsultarCaptacion;
import com.credusan.captaciones.dominio.modelos.Captacion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/captaciones")
public class ControladorConsultarCaptacion {

    ServicioConsultarCaptacion servicio;

    public ControladorConsultarCaptacion(ServicioConsultarCaptacion servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/{idAsociado}")
    public ResponseEntity<List<Captacion>> update(@PathVariable("idAsociado") Integer idAsociado) throws Exception {
        List<Captacion> lista = servicio.getAllByIdAsociado(idAsociado);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

}
