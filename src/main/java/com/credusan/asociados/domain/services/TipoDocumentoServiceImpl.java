package com.credusan.asociados.domain.services;

import com.credusan.asociados.domain.models.TipoDocumento;
import com.credusan.asociados.domain.ports.input.TipoDocumentoService;
import com.credusan.asociados.domain.ports.output.TipoDocumentoPersistence;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoDocumentoServiceImpl implements TipoDocumentoService {

    private TipoDocumentoPersistence persistence;

    public TipoDocumentoServiceImpl(TipoDocumentoPersistence persistence) {
        this.persistence = persistence;
    }

    @Override
    public List<TipoDocumento> getAll() throws Exception {
        return persistence.getAll();
    }
}
