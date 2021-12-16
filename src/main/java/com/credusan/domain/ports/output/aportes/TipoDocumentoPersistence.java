package com.credusan.domain.ports.output.aportes;

import com.credusan.domain.models.aportes.TipoDocumento;

import java.util.List;

public interface TipoDocumentoPersistence {
    List<TipoDocumento> getAll() throws Exception;
}
