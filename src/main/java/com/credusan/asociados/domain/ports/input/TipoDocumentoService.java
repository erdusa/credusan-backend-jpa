package com.credusan.asociados.domain.ports.input;

import com.credusan.asociados.domain.models.TipoDocumento;

import java.util.List;

public interface TipoDocumentoService {
    List<TipoDocumento> getAll() throws Exception;
}
