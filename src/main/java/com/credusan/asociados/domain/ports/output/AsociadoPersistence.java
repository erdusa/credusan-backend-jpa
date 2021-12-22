package com.credusan.asociados.domain.ports.output;

import com.credusan.asociados.domain.models.Asociado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AsociadoPersistence {

    Asociado save(Asociado asociado) throws Exception;

    Asociado getById(Integer idAsociado) throws Exception;

    Page<Asociado> getAll(Pageable page) throws Exception;

    List<Asociado> getAllByNameOrSurnames(String nombres) throws Exception;
}
