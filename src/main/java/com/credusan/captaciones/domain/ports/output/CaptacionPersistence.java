package com.credusan.captaciones.domain.ports.output;

import com.credusan.captaciones.domain.models.Captacion;

import java.util.List;

public interface CaptacionPersistence {

    Captacion save(Captacion captacion) throws Exception;

    Integer getMaxNumeroCuentaByTipoCaptacion(Integer IdTipoCaptacion) throws Exception;

    List<Captacion> getAllByIdAsociado(Integer idAsociado);

    Captacion getById(Integer idCaptacion);
}
