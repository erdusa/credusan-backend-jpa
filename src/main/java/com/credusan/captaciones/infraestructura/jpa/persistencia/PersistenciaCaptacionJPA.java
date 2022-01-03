package com.credusan.captaciones.infraestructura.jpa.persistencia;

import com.credusan.asociados.dominio.modelos.Asociado;
import com.credusan.captaciones.dominio.enums.EnumTipoCaptacion;
import com.credusan.captaciones.dominio.enums.EnumTipoEstadoCaptacion;
import com.credusan.captaciones.dominio.modelos.Captacion;
import com.credusan.captaciones.dominio.modelos.TipoCaptacion;
import com.credusan.captaciones.dominio.modelos.TipoEstadoCaptacion;
import com.credusan.captaciones.dominio.puertos.PersistenciaCaptacion;
import com.credusan.captaciones.infraestructura.jpa.daos.RepositorioCaptacion;
import com.credusan.captaciones.infraestructura.jpa.entidades.EntidadCaptacion;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PersistenciaCaptacionJPA implements PersistenciaCaptacion {

    private RepositorioCaptacion repo;

    public PersistenciaCaptacionJPA(RepositorioCaptacion repo) {
        this.repo = repo;
    }

    @Override
    public Captacion save(Captacion captacion) throws Exception {
        return repo.save(new EntidadCaptacion(captacion)).toCaptacion();
    }

    @Override
    public Captacion crearCuentaAportes(Asociado asociado) {
        Captacion captacion = new Captacion();
        captacion.setTipoEstadoCaptacion(new TipoEstadoCaptacion(EnumTipoEstadoCaptacion.ACTIVA.id));
        captacion.setTipoCaptacion(new TipoCaptacion(EnumTipoCaptacion.APORTES.id));
        captacion.setFechaApertura(LocalDate.now());
        captacion.setNumeroCuenta(this.getMaxNumeroCuentaByTipoCaptacion(EnumTipoCaptacion.APORTES.id));
        captacion.setSaldo((double) 0);
        captacion.setAsociado(asociado);
        return repo.save(new EntidadCaptacion(captacion)).toCaptacion();
    }

    @Override
    public Integer getMaxNumeroCuentaByTipoCaptacion(Integer idTipoCaptacion) {
        Integer numeroCuenta = repo.findMaxNumeroCuentaByTipoCaptacion(idTipoCaptacion);
        return (numeroCuenta == null ? 0 : numeroCuenta) + 1;
    }

    @Override
    public List<Captacion> getAllByIdAsociado(Integer idAsociado) throws Exception {
        return repo.findAllByIdAsociado(idAsociado).stream()
                .map(EntidadCaptacion::toCaptacion)
                .collect(Collectors.toList());
    }

    @Override
    public Captacion getById(Integer idCaptacion) {
        return repo.getById(idCaptacion).toCaptacion();
    }

    @Override
    public Captacion getCuentaAportes(Integer idAsociado) throws Exception {
        return this.getAllByIdAsociado(idAsociado).stream()
                .filter(c -> c.getTipoCaptacion().getIdTipoCaptacion().equals(EnumTipoCaptacion.APORTES.id)
                        && c.getTipoEstadoCaptacion().getIdTipoEstadoCaptacion().equals(EnumTipoEstadoCaptacion.ACTIVA.id))
                .findAny().orElse(null);

    }

}
