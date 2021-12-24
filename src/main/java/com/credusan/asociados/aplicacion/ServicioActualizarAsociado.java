package com.credusan.asociados.aplicacion;

import com.credusan.asociados.dominio.modelos.Asociado;
import com.credusan.asociados.dominio.modelos.Beneficiario;
import com.credusan.asociados.dominio.puertos.PersistenciaAsociado;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class ServicioActualizarAsociado {

    private final PersistenciaAsociado persistence;

    public ServicioActualizarAsociado(PersistenciaAsociado persistenciaAsociado) {
        this.persistence = persistenciaAsociado;
    }

    public Asociado update(Integer idAsociado, Asociado asociado) throws Exception {

        if (persistence.getById(idAsociado).getNumeroDocumento() == null) {
            throw new Exception("No existe el asociado con id = " + idAsociado);
        }

        if (verificarSiElPorcentajeBeneficiariosEstaErrado(asociado)) {
            throw new Exception("Los porcentajes asignados a los beneficiarios deben sumar 100");
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
