package com.credusan.asociados.dominio.puertos;

import com.credusan.asociados.dominio.modelos.TipoDocumento;

import java.util.List;

public interface TipoDocumentoPersistence {
    List<TipoDocumento> getAll() throws Exception;
}
