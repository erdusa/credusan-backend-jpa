package com.credusan.adapters.jpa.aportes.persistence;

import com.credusan.adapters.jpa.aportes.daos.TipoDocumentoRepository;
import com.credusan.adapters.jpa.aportes.entities.TipoDocumentoEntity;
import com.credusan.domain.models.aportes.TipoDocumento;
import com.credusan.domain.ports.output.aportes.TipoDocumentoPersistence;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TipoDocumentoPersistenceJPA implements TipoDocumentoPersistence {

    private TipoDocumentoRepository repo;

    public TipoDocumentoPersistenceJPA(TipoDocumentoRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<TipoDocumento> getAll() throws Exception {
        return repo.findAll().stream()
                .map(TipoDocumentoEntity::toTipoDocumento)
                .collect(Collectors.toList());
    }
}
