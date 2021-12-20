package com.credusan.asociados.domain.services;

import com.credusan.asociados.domain.models.Asociado;
import com.credusan.asociados.domain.models.Beneficiario;
import com.credusan.asociados.domain.ports.output.AsociadoPersistence;
import com.credusan.captaciones.domain.ports.output.CaptacionPersistence;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class AsociadoUpdaterService {

    private final AsociadoPersistence persistence;

    public AsociadoUpdaterService(AsociadoPersistence asociadoPersistence, CaptacionPersistence captacionPersistence) {
        this.persistence = asociadoPersistence;
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
