package com.credusan.domain.ports.input.aportes;

import com.credusan.domain.models.aportes.Asociado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AsociadoService {

    Asociado create(Asociado asociado) throws Exception;

    Asociado update(Integer idAsociado, Asociado asociado) throws Exception;

    Page<Asociado> getAll(Pageable page) throws Exception;

    Asociado getById(Integer idAsociado) throws Exception;

    void retirarAsociado(Integer idAsociado) throws Exception;

}
