package com.credusan.captaciones.domain.ports.input;

import com.credusan.captaciones.domain.models.Captacion;

import java.util.List;

public interface CaptacionService {

    List<Captacion> getAllByIdAsociado(Integer idAsociado) throws Exception;

    Captacion create(Captacion captacion) throws Exception;

    Captacion update(Integer idCaptacion, Captacion captacion) throws Exception;

}
