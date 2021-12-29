package com.credusan.captaciones.aplicacion;

import com.credusan.captaciones.dominio.enums.EnumTipoCaptacion;
import com.credusan.captaciones.dominio.enums.EnumTipoEstadoCaptacion;
import com.credusan.captaciones.dominio.modelos.Captacion;
import com.credusan.captaciones.dominio.puertos.PersistenciaCaptacion;
import org.springframework.stereotype.Service;

@Service
public class ServicioSaldarCaptacion {

    private static final String NO_SE_PUEDE_SALDAR_LA_CUENTA_DE_APORTES = "No se puede saldar la cuenta de aportes";
    private static final String LA_CAPTACION_YA_ESTA_SALDADA = "La captación ya está saldada";

    private PersistenciaCaptacion repo;

    public ServicioSaldarCaptacion(PersistenciaCaptacion repo) {
        this.repo = repo;
    }

    public Boolean saldar(Integer idCaptacion) throws Exception {
        Captacion captacion = repo.getById(idCaptacion);
        boolean esAportes = captacion.getTipoCaptacion().getIdTipoCaptacion().equals(EnumTipoCaptacion.APORTES.id);
        if (esAportes) {
            throw new Exception(NO_SE_PUEDE_SALDAR_LA_CUENTA_DE_APORTES);
        }

        boolean estaSaldada = captacion.getTipoEstadoCaptacion().getIdTipoEstadoCaptacion().equals(EnumTipoEstadoCaptacion.SALDADA.id);
        if (estaSaldada) {
            throw new Exception(LA_CAPTACION_YA_ESTA_SALDADA);
        }
        //TODO: retirar el saldo de cajas

        captacion.getTipoEstadoCaptacion().setIdTipoEstadoCaptacion(EnumTipoEstadoCaptacion.SALDADA.id);
        Captacion captacionSaldada = repo.save(captacion);

        return captacionSaldada.getTipoEstadoCaptacion().getIdTipoEstadoCaptacion().equals(EnumTipoEstadoCaptacion.SALDADA.id);
    }

}
