package com.credusan.domain.services;

import com.credusan.domain.models.aportes.TipoDocumento;
import com.credusan.domain.ports.input.aportes.TipoDocumentoService;
import com.credusan.domain.ports.output.aportes.TipoDocumentoPersistence;
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
