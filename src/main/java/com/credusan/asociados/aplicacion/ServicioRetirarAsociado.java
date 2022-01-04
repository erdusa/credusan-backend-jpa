package com.credusan.asociados.aplicacion;

import com.credusan.asociados.dominio.modelos.Asociado;
import com.credusan.asociados.dominio.puertos.PersistenciaAsociado;
import com.credusan.captaciones.dominio.enums.EnumTipoCaptacion;
import com.credusan.captaciones.dominio.enums.EnumTipoEstadoCaptacion;
import com.credusan.captaciones.dominio.modelos.Captacion;
import com.credusan.captaciones.dominio.puertos.PersistenciaCaptacion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServicioRetirarAsociado {
    static final String NO_TIENE_CUENTA_DE_APORTES_ACTIVA = "No tiene cuenta de aportes activa";
    static final String EL_ASOCIADO_NO_ESTA_ACTIVO = "El asociado no está activo";
    static final String EL_ASOCIADO_TIENE_CAPTACIONES_ACTIVAS = "El asociado tiene captaciones activas";

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

        List<Captacion> captaciones = captacionPersistence.getAllByIdAsociado(asociadoRetirado.getIdAsociado());
        boolean tieneCaptacionesActivas = captaciones.stream()
                .anyMatch(c -> c.getTipoEstadoCaptacion().getIdTipoEstadoCaptacion().equals(EnumTipoEstadoCaptacion.ACTIVA.id)
                        && !c.getTipoCaptacion().getIdTipoCaptacion().equals(EnumTipoCaptacion.APORTES.id));
        if (tieneCaptacionesActivas) {
            throw new Exception(EL_ASOCIADO_TIENE_CAPTACIONES_ACTIVAS);
        }

        cuentaAportes.getTipoEstadoCaptacion().setIdTipoEstadoCaptacion(EnumTipoEstadoCaptacion.SALDADA.id);

        Captacion captacionSaldada = captacionPersistence.save(cuentaAportes);

        return !asociadoRetirado.getActivo();
    }


}
