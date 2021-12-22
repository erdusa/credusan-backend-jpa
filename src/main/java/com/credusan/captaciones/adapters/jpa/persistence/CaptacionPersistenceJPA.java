package com.credusan.captaciones.adapters.jpa.persistence;

import com.credusan.captaciones.adapters.jpa.daos.CaptacionRepository;
import com.credusan.captaciones.adapters.jpa.entities.CaptacionEntity;
import com.credusan.captaciones.domain.enums.EnumTipoCaptacion;
import com.credusan.captaciones.domain.enums.EnumTipoEstadoCaptacion;
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
    public Captacion save(Captacion captacion) {
        return repo.save(new CaptacionEntity(captacion)).toCaptacion();
    }

    @Override
    public Integer getMaxNumeroCuentaByTipoCaptacion(Integer idTipoCaptacion) {
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

    @Override
    public Captacion getCuentaAportes(Integer idAsociado) {
        return this.getAllByIdAsociado(idAsociado).stream()
                .filter(c -> c.getTipoCaptacion().getIdTipoCaptacion().equals(EnumTipoCaptacion.APORTES.id)
                        && c.getTipoEstadoCaptacion().getIdTipoEstadoCaptacion().equals(EnumTipoEstadoCaptacion.ACTIVA.id))
                .findAny().orElse(null);

    }

}
