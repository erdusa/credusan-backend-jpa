package com.credusan.captaciones.aplicacion;

import com.credusan.captaciones.dominio.modelos.TipoCaptacion;
import com.credusan.captaciones.dominio.puertos.PersistenciaTipoCaptacion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioConsultarTipoCaptacion {

    PersistenciaTipoCaptacion repo;

    public ServicioConsultarTipoCaptacion(PersistenciaTipoCaptacion repo) {
        this.repo = repo;
    }

    public List<TipoCaptacion> getAll() {
        return repo.getAll();
    }
}
