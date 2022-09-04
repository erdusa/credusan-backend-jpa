package com.credusan.asociados.aplicacion;

import com.credusan.asociados.dominio.modelos.Asociado;
import com.credusan.asociados.dominio.modelos.Beneficiario;
import com.credusan.asociados.dominio.puertos.PersistenciaAsociado;
import com.credusan.captaciones.dominio.puertos.PersistenciaCaptacion;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;

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
            throw new ValidationException("El identificador del asociado no debe tener valor");
        }

        if (verificarSiElPorcentajeBeneficiariosEstaErrado(asociado)) {
            throw new ValidationException("Los porcentajes asignados a los beneficiarios deben sumar 100");
        }
        if (asociado.getBeneficiarios() != null) {
            asociado.getBeneficiarios().forEach(beneficiario -> beneficiario.setAsociado(asociado));
        }

        asociado.setActivo(true);
        Asociado asociadoCreado = persistence.save(asociado);

        captacionPersistence.crearCuentaAportes(asociadoCreado);

        return asociadoCreado;
    }

    private boolean verificarSiTieneIdAsociado(@NonNull Asociado asociado) {
        return asociado.getIdAsociado() != null;
    }

    private boolean verificarSiElPorcentajeBeneficiariosEstaErrado(@NonNull Asociado asociado) {
        if (asociado.getBeneficiarios() == null || asociado.getBeneficiarios().isEmpty()) {
            return false;
        }
        int totalPorcentaje = asociado.getBeneficiarios().stream()
                .mapToInt(Beneficiario::getPorcentaje)
                .sum();

        return (totalPorcentaje != 100);
    }
}
