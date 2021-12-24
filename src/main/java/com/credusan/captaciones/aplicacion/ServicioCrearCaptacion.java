package com.credusan.captaciones.aplicacion;

import com.credusan.captaciones.dominio.enums.EnumTipoCaptacion;
import com.credusan.captaciones.dominio.enums.EnumTipoEstadoCaptacion;
import com.credusan.captaciones.dominio.modelos.Captacion;
import com.credusan.captaciones.dominio.modelos.TipoEstadoCaptacion;
import com.credusan.captaciones.dominio.puertos.PersistenciaCaptacion;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ServicioCrearCaptacion {

    private PersistenciaCaptacion repo;

    public ServicioCrearCaptacion(PersistenciaCaptacion repo) {
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

    private boolean verificarSiEsAportesYAsociadoYaTieneUnaActiva(Captacion captacion) {
        return captacion.getTipoCaptacion().getIdTipoCaptacion().equals(EnumTipoCaptacion.APORTES.id)
                && repo.getAllByIdAsociado(captacion.getAsociado().getIdAsociado()).stream()
                .anyMatch(c -> c.getTipoCaptacion().getIdTipoCaptacion().equals(EnumTipoCaptacion.APORTES.id)
                        && c.getTipoEstadoCaptacion().getIdTipoEstadoCaptacion().equals(EnumTipoEstadoCaptacion.ACTIVA.id));
    }


}
