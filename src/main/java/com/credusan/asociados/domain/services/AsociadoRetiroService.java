package com.credusan.asociados.domain.services;

import com.credusan.captaciones.domain.enums.EnumTipoEstadoCaptacion;
import com.credusan.captaciones.domain.models.Captacion;
import com.credusan.captaciones.domain.ports.output.CaptacionPersistence;
import org.springframework.stereotype.Service;

@Service
public class AsociadoRetiroService {

    private final CaptacionPersistence captacionPersistence;

    public AsociadoRetiroService(CaptacionPersistence captacionPersistence) {
        this.captacionPersistence = captacionPersistence;
    }

    public Boolean retirarAsociado(int idAsociado) {

        Captacion cuentaAportes = captacionPersistence.getCuentaAportes(idAsociado);

        if (cuentaAportes == null) {
            return false;
        }

        cuentaAportes.getTipoEstadoCaptacion().setIdTipoEstadoCaptacion(EnumTipoEstadoCaptacion.SALDADA.id);

        captacionPersistence.save(cuentaAportes);

        return true;
    }


}
