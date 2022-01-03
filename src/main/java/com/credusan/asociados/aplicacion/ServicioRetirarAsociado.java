package com.credusan.asociados.aplicacion;

import com.credusan.asociados.dominio.modelos.Asociado;
import com.credusan.asociados.dominio.puertos.PersistenciaAsociado;
import com.credusan.captaciones.dominio.enums.EnumTipoEstadoCaptacion;
import com.credusan.captaciones.dominio.modelos.Captacion;
import com.credusan.captaciones.dominio.puertos.PersistenciaCaptacion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioRetirarAsociado {
    private static final String NO_TIENE_CUENTA_DE_APORTES_ACTIVA = "No tiene cuenta de aportes activa";
    private static final String EL_ASOCIADO_NO_ESTA_ACTIVO = "El asociado no está activo";

    private final PersistenciaAsociado persistenciaAsociado;
    private final PersistenciaCaptacion captacionPersistence;

    public ServicioRetirarAsociado(PersistenciaAsociado persistenciaAsociado, PersistenciaCaptacion captacionPersistence) {
        this.persistenciaAsociado = persistenciaAsociado;
        this.captacionPersistence = captacionPersistence;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean retirarAsociado(int idAsociado) throws Exception {

        Asociado asociado = persistenciaAsociado.getById(idAsociado);
        if (!asociado.getActivo()) {
            throw new Exception(EL_ASOCIADO_NO_ESTA_ACTIVO);
        }

        asociado.setActivo(false);
        Asociado asociadoRetirado = persistenciaAsociado.save(asociado);

        Captacion cuentaAportes = captacionPersistence.getCuentaAportes(asociadoRetirado.getIdAsociado());
        if (cuentaAportes == null) {
            throw new Exception(NO_TIENE_CUENTA_DE_APORTES_ACTIVA);
        }

        cuentaAportes.getTipoEstadoCaptacion().setIdTipoEstadoCaptacion(EnumTipoEstadoCaptacion.SALDADA.id);

        Captacion captacionSaldada = captacionPersistence.save(cuentaAportes);

        return !asociadoRetirado.getActivo();
    }


}
