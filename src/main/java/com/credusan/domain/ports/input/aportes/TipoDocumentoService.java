package com.credusan.domain.ports.input.aportes;

import com.credusan.domain.models.aportes.TipoDocumento;

import java.util.List;

public interface TipoDocumentoService {
    List<TipoDocumento> getAll() throws Exception;
}
