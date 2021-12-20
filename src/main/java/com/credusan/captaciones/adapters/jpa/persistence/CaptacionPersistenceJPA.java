package com.credusan.captaciones.adapters.jpa.persistence;

import com.credusan.captaciones.adapters.jpa.daos.CaptacionRepository;
import com.credusan.captaciones.adapters.jpa.entities.CaptacionEntity;
import com.credusan.captaciones.domain.models.Captacion;
import com.credusan.captaciones.domain.ports.output.CaptacionPersistence;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CaptacionPersistenceJPA implements CaptacionPersistence {

    private CaptacionRepository repo;

    public CaptacionPersistenceJPA(CaptacionRepository repo) {
        this.repo = repo;
    }

    @Override
    public Captacion save(Captacion captacion) throws Exception {
        return repo.save(new CaptacionEntity(captacion)).toCaptacion();
    }

    @Override
    public Integer getMaxNumeroCuentaByTipoCaptacion(Integer idTipoCaptacion) throws Exception {
        Integer numeroCuenta = repo.findMaxNumeroCuentaByTipoCaptacion(idTipoCaptacion);
        return (numeroCuenta == null ? 0 : numeroCuenta) + 1;
    }

    @Override
    public List<Captacion> getAllByIdAsociado(Integer idAsociado) {
        return repo.findAllByIdAsociado(idAsociado).stream()
                .map(CaptacionEntity::toCaptacion)
                .collect(Collectors.toList());
    }

    @Override
    public Captacion getById(Integer idCaptacion) {
        return repo.getById(idCaptacion).toCaptacion();
    }

}
