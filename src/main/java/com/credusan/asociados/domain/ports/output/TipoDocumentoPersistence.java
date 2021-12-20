package com.credusan.asociados.domain.ports.output;

import com.credusan.asociados.domain.models.TipoDocumento;

import java.util.List;

public interface TipoDocumentoPersistence {
    List<TipoDocumento> getAll() throws Exception;
}
