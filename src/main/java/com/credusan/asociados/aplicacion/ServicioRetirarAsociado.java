package com.credusan.asociados.aplicacion;

import com.credusan.captaciones.dominio.enums.EnumTipoEstadoCaptacion;
import com.credusan.captaciones.dominio.modelos.Captacion;
import com.credusan.captaciones.dominio.puertos.PersistenciaCaptacion;
import org.springframework.stereotype.Service;

@Service
public class ServicioRetirarAsociado {

    private final PersistenciaCaptacion captacionPersistence;

    public ServicioRetirarAsociado(PersistenciaCaptacion captacionPersistence) {
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
