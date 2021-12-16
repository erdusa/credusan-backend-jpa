package com.credusan.domain.ports.output.aportes;

import com.credusan.domain.models.aportes.Asociado;

import java.util.List;

public interface AsociadoPersistence {

    Asociado create(Asociado asociado) throws Exception;

    Asociado update(Asociado asociado) throws Exception;

    Asociado getById(Integer idAsociado) throws Exception;

    List<Asociado> getAll() throws Exception;
}
