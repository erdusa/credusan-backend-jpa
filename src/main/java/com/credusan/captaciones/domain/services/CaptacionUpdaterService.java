package com.credusan.captaciones.domain.services;

import com.credusan.captaciones.domain.models.Captacion;
import com.credusan.captaciones.domain.ports.output.CaptacionPersistence;
import org.springframework.stereotype.Service;

@Service
public class CaptacionUpdaterService {

    private CaptacionPersistence repo;

    public CaptacionUpdaterService(CaptacionPersistence repo) {
        this.repo = repo;
    }

    public Captacion update(Integer idCaptacion, Captacion captacion) throws Exception {
        Captacion captacionU = repo.getById(idCaptacion);

        if (!captacion.getAsociado().getIdAsociado().equals(captacionU.getAsociado().getIdAsociado())) {
            throw new Exception("No se puede cambiar el propietario de la captación");
        }

        if (!captacion.getTipoCaptacion().getIdTipoCaptacion().equals(captacionU.getTipoCaptacion().getIdTipoCaptacion())) {
            throw new Exception("No se puede cambiar el tipo de captación");
        }

        if (!captacion.getNumeroCuenta().equals(captacionU.getNumeroCuenta())) {
            throw new Exception("No se puede cambiar el número de cuenta");
        }

        if (!captacion.getFechaApertura().equals(captacionU.getFechaApertura())) {
            throw new Exception("No se puede cambiar la fecha de apertura");
        }

        if (captacion.getSaldo() < 0) {
            throw new Exception("El saldo de la cuenta no puede ser negativo");
        }

        return repo.save(captacion);
    }

}
