package com.credusan.domain.ports.input.aportes;

import com.credusan.domain.models.aportes.Captacion;

import java.util.List;

public interface CaptacionService {

    List<Captacion> getAllByIdAsociado(Integer idAsociado);

}
