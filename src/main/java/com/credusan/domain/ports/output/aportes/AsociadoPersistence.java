package com.credusan.domain.ports.output.aportes;

import com.credusan.domain.models.aportes.Asociado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AsociadoPersistence {

    Asociado create(Asociado asociado) throws Exception;

    Asociado update(Asociado asociado) throws Exception;

    Asociado getById(Integer idAsociado) throws Exception;

    Page<Asociado> getAll(Pageable page) throws Exception;
}
