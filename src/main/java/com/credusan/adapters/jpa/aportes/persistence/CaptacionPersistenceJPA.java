package com.credusan.adapters.jpa.aportes.persistence;

import com.credusan.adapters.jpa.aportes.daos.CaptacionRepository;
import com.credusan.adapters.jpa.aportes.entities.CaptacionEntity;
import com.credusan.domain.models.aportes.Captacion;
import com.credusan.domain.ports.output.aportes.CaptacionPersistence;
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
    public Captacion create(Captacion captacion) throws Exception {
        return repo.save(new CaptacionEntity(captacion)).toCaptacion();
    }

    @Override
    public Integer getMaxNumeroCuentaByTipoCaptacion(Integer idTipoCaptacion) throws Exception {
        return repo.findMaxNumeroCuentaByTipoCaptacion(idTipoCaptacion);
    }

    @Override
    public List<Captacion> getAllByIdAsociado(Integer idAsociado) {
        return repo.findAllByIdAsociado(idAsociado).stream()
                .map(CaptacionEntity::toCaptacion)
                .collect(Collectors.toList());
    }

}
