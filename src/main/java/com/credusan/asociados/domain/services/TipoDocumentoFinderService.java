package com.credusan.asociados.domain.services;

import com.credusan.asociados.domain.models.TipoDocumento;
import com.credusan.asociados.domain.ports.output.TipoDocumentoPersistence;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoDocumentoFinderService {

    private TipoDocumentoPersistence persistence;

    public TipoDocumentoFinderService(TipoDocumentoPersistence persistence) {
        this.persistence = persistence;
    }

    public List<TipoDocumento> getAll() throws Exception {
        return persistence.getAll();
    }
}
