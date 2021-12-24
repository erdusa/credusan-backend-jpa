package com.credusan.captaciones.dominio.puertos;

import com.credusan.captaciones.dominio.modelos.Captacion;

import java.util.List;

public interface PersistenciaCaptacion {

    Captacion save(Captacion captacion);

    Integer getMaxNumeroCuentaByTipoCaptacion(Integer IdTipoCaptacion);

    List<Captacion> getAllByIdAsociado(Integer idAsociado);

    Captacion getById(Integer idCaptacion);

    Captacion getCuentaAportes(Integer idAsociado);
}
