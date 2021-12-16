package com.credusan.domain.ports.input.aportes;

import com.credusan.domain.models.aportes.Asociado;

import java.util.List;

public interface AsociadoService {

    Asociado create(Asociado asociado) throws Exception;

    Asociado update(Integer idAsociado, Asociado asociado) throws Exception;

    List<Asociado> getAll() throws Exception;

    Asociado getById(Integer idAsociado) throws Exception;

    void retirarAsociado(Integer idAsociado) throws Exception;

}
