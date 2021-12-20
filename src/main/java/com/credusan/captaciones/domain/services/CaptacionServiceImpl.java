package com.credusan.captaciones.domain.services;

import com.credusan.captaciones.domain.models.Captacion;
import com.credusan.captaciones.domain.enums.EnumTipoCaptacion;
import com.credusan.captaciones.domain.enums.EnumTipoEstadoCaptacion;
import com.credusan.captaciones.domain.models.TipoEstadoCaptacion;
import com.credusan.captaciones.domain.ports.input.CaptacionService;
import com.credusan.captaciones.domain.ports.output.CaptacionPersistence;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CaptacionServiceImpl implements CaptacionService {

    private CaptacionPersistence repo;

    public CaptacionServiceImpl(CaptacionPersistence repo) {
        this.repo = repo;
    }

    @Override
    public List<Captacion> getAllByIdAsociado(Integer idAsociado) throws Exception {
        return repo.getAllByIdAsociado(idAsociado);
    }

    @Override
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

    @Override
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
