package com.credusan.asociados.domain.ports.input;

import com.credusan.asociados.domain.models.Asociado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AsociadoService {

    Asociado create(Asociado asociado) throws Exception;

    Asociado update(Integer idAsociado, Asociado asociado) throws Exception;

    Page<Asociado> getAll(Pageable page) throws Exception;

    Asociado getById(Integer idAsociado) throws Exception;

    void retirarAsociado(Integer idAsociado) throws Exception;

    List<Asociado> getAllByNames(String nombres) throws Exception;

}
