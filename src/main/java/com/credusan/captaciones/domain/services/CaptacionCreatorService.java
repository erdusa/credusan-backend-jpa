package com.credusan.captaciones.domain.services;

import com.credusan.captaciones.domain.enums.EnumTipoCaptacion;
import com.credusan.captaciones.domain.enums.EnumTipoEstadoCaptacion;
import com.credusan.captaciones.domain.models.Captacion;
import com.credusan.captaciones.domain.models.TipoEstadoCaptacion;
import com.credusan.captaciones.domain.ports.output.CaptacionPersistence;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CaptacionCreatorService {

    private CaptacionPersistence repo;

    public CaptacionCreatorService(CaptacionPersistence repo) {
        this.repo = repo;
    }

    public Captacion create(Captacion captacion) throws Exception {
        if (captacion.getIdCaptacion() != null) {
            throw new Exception("El identificador de la captación no debe tener valor");
        }

        if (verificarSiEsAportesYAsociadoYaTieneUnaActiva(captacion)) {
            throw new Exception("El asociado ya tiene una cuenta de aportes activa");
        }

        captacion.setFechaApertura(LocalDate.now());
        captacion.setTipoEstadoCaptacion(new TipoEstadoCaptacion(EnumTipoEstadoCaptacion.ACTIVA.id));
        captacion.setFechaApertura(LocalDate.now());
        captacion.setNumeroCuenta(repo.getMaxNumeroCuentaByTipoCaptacion(captacion.getTipoCaptacion().getIdTipoCaptacion()));
        captacion.setSaldo((double) 0);
        return repo.save(captacion);
    }

    private boolean verificarSiEsAportesYAsociadoYaTieneUnaActiva(Captacion captacion) throws Exception {
        return captacion.getTipoCaptacion().getIdTipoCaptacion().equals(EnumTipoCaptacion.APORTES.id)
                && repo.getAllByIdAsociado(captacion.getAsociado().getIdAsociado()).stream()
                .anyMatch(c -> c.getTipoCaptacion().getIdTipoCaptacion().equals(EnumTipoCaptacion.APORTES.id)
                        && c.getTipoEstadoCaptacion().getIdTipoEstadoCaptacion().equals(EnumTipoEstadoCaptacion.ACTIVA.id));
    }


}
