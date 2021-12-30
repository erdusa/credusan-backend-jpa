package com.credusan.asociados.aplicacion;

import com.credusan.asociados.dominio.modelos.Asociado;
import com.credusan.asociados.dominio.modelos.Beneficiario;
import com.credusan.asociados.dominio.puertos.PersistenciaAsociado;
import com.credusan.captaciones.dominio.enums.EnumTipoCaptacion;
import com.credusan.captaciones.dominio.enums.EnumTipoEstadoCaptacion;
import com.credusan.captaciones.dominio.modelos.Captacion;
import com.credusan.captaciones.dominio.modelos.TipoCaptacion;
import com.credusan.captaciones.dominio.modelos.TipoEstadoCaptacion;
import com.credusan.captaciones.dominio.puertos.PersistenciaCaptacion;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class ServicioCrearAsociado {

    private final PersistenciaAsociado persistence;
    private final PersistenciaCaptacion captacionPersistence;

    public ServicioCrearAsociado(PersistenciaAsociado persistenciaAsociado, PersistenciaCaptacion captacionPersistence) {
        this.persistence = persistenciaAsociado;
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

        asociado.setActivo(true);
        Asociado asociadoCreado = persistence.save(asociado);

        crearCaptacion(asociadoCreado);

        return asociadoCreado;
    }

    private void crearCaptacion(Asociado asociado) {
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
