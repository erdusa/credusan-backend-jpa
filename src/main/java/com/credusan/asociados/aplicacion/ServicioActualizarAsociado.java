package com.credusan.asociados.aplicacion;

import com.credusan.asociados.dominio.modelos.Asociado;
import com.credusan.asociados.dominio.modelos.Beneficiario;
import com.credusan.asociados.dominio.puertos.PersistenciaAsociado;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class ServicioActualizarAsociado {

    private static final String LOS_PORCENTAJES_ASIGNADOS_A_LOS_BENEFICIARIOS_DEBEN_SUMAR_100 = "Los porcentajes asignados a los beneficiarios deben sumar 100";
    private static final String NO_EXISTE_EL_ASOCIADO = "No existe el asociado";
    private static final String NO_PUEDE_INACTIVAR_EL_ASOCIADO_DEBERIA_RETIRARLO = "No puede inactivar el asociado, debería retirarlo";

    private final PersistenciaAsociado persistence;

    public ServicioActualizarAsociado(PersistenciaAsociado persistenciaAsociado) {
        this.persistence = persistenciaAsociado;
    }

    public Asociado update(Integer idAsociado, Asociado asociado) throws Exception {
        Asociado asociadoU = persistence.getById(idAsociado);

        if (asociadoU.getIdAsociado() == null) {
            throw new Exception(NO_EXISTE_EL_ASOCIADO);
        }

        if (verificarSiElPorcentajeBeneficiariosEstaErrado(asociado)) {
            throw new Exception(LOS_PORCENTAJES_ASIGNADOS_A_LOS_BENEFICIARIOS_DEBEN_SUMAR_100);
        }

        boolean esInactivarAsociado = asociadoU.getActivo() && !asociado.getActivo();
        if (esInactivarAsociado) {
            throw new Exception(NO_PUEDE_INACTIVAR_EL_ASOCIADO_DEBERIA_RETIRARLO);
        }

        if (asociado.getBeneficiarios() != null) {
            asociado.getBeneficiarios().forEach(beneficiario -> beneficiario.setAsociado(asociado));
        }

        return persistence.save(asociado);
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
