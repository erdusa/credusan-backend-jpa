package com.credusan.domain.ports.output.aportes;

import com.credusan.domain.models.aportes.Captacion;

import java.util.List;

public interface CaptacionPersistence {

    Captacion create(Captacion captacion) throws Exception;

    Integer getMaxNumeroCuentaByTipoCaptacion(Integer IdTipoCaptacion) throws Exception;

    List<Captacion> getAllByIdAsociado(Integer idAsociado);
}
