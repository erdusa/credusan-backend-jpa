package com.credusan.captaciones.infraestructura.jpa.persistencia;

import com.credusan.captaciones.dominio.modelos.TipoCaptacion;
import com.credusan.captaciones.dominio.puertos.PersistenciaTipoCaptacion;
import com.credusan.captaciones.infraestructura.jpa.daos.RepositorioTipoCaptacion;
import com.credusan.captaciones.infraestructura.jpa.entidades.EntidadTipoCaptacion;

import java.util.List;
import java.util.stream.Collectors;

//@Repository
public class PersistenciaTipoCaptacionJPA implements PersistenciaTipoCaptacion {

    RepositorioTipoCaptacion repo;

    public PersistenciaTipoCaptacionJPA(RepositorioTipoCaptacion repo) {
        this.repo = repo;
    }

    @Override
    public List<TipoCaptacion> getAll() {
        return repo.findAll().stream()
                .map(EntidadTipoCaptacion::toTipoCaptacion)
                .collect(Collectors.toList());
    }
}
