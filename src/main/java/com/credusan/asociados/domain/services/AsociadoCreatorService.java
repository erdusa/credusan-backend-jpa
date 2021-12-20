package com.credusan.asociados.domain.services;

import com.credusan.asociados.domain.models.Asociado;
import com.credusan.asociados.domain.models.Beneficiario;
import com.credusan.asociados.domain.ports.output.AsociadoPersistence;
import com.credusan.captaciones.domain.enums.EnumTipoCaptacion;
import com.credusan.captaciones.domain.enums.EnumTipoEstadoCaptacion;
import com.credusan.captaciones.domain.models.Captacion;
import com.credusan.captaciones.domain.models.TipoCaptacion;
import com.credusan.captaciones.domain.models.TipoEstadoCaptacion;
import com.credusan.captaciones.domain.ports.output.CaptacionPersistence;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class AsociadoCreatorService {

    private final AsociadoPersistence persistence;
    private final CaptacionPersistence captacionPersistence;

    public AsociadoCreatorService(AsociadoPersistence asociadoPersistence, CaptacionPersistence captacionPersistence) {
        this.persistence = asociadoPersistence;
        this.captacionPersistence = captacionPersistence;
    }

    @Transactional(rollbackFor = Exception.class)
    public Asociado create(Asociado asociado) throws Exception {

        if (verificarSiTieneIdAsociado(asociado)) {
            throw new Exception("El identificador del asociado no debe tener valor");
        }

        if (verificarSiElPorcentajeBeneficiariosEstaErrado(asociado)) {
            throw new Exception("Los porcentajes asignados a los beneficiarios deben sumar 100");
        }
        if (asociado.getBeneficiarios() != null) {
            asociado.getBeneficiarios().forEach(beneficiario -> beneficiario.setAsociado(asociado));
        }

        Asociado asociadoCreado = persistence.save(asociado);

        crearCaptacion(asociadoCreado);

        return asociadoCreado;
    }

    private void crearCaptacion(Asociado asociado) throws Exception {
        Captacion captacion = new Captacion();
        captacion.setTipoEstadoCaptacion(new TipoEstadoCaptacion(EnumTipoEstadoCaptacion.ACTIVA.id));
        captacion.setTipoCaptacion(new TipoCaptacion(EnumTipoCaptacion.APORTES.id));
        captacion.setFechaApertura(LocalDate.now());
        captacion.setNumeroCuenta(captacionPersistence.getMaxNumeroCuentaByTipoCaptacion(EnumTipoCaptacion.APORTES.id));
        captacion.setSaldo((double) 0);
        captacion.setAsociado(asociado);
        captacionPersistence.save(captacion);
    }

    private boolean verificarSiTieneIdAsociado(@NonNull Asociado asociado) {
        return asociado.getIdAsociado() != null;
    }

    private boolean verificarSiElPorcentajeBeneficiariosEstaErrado(@NonNull Asociado asociado) {
        if (asociado.getBeneficiarios() == null || asociado.getBeneficiarios().size() == 0) {
            return false;
        }
        int totalPorcentaje = asociado.getBeneficiarios().stream()
                .mapToInt(Beneficiario::getPorcentaje)
                .sum();

        return (totalPorcentaje != 100);
    }
}
