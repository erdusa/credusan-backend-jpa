package com.credusan.asociados.adapters.jpa.persistence;

import com.credusan.asociados.adapters.jpa.daos.TipoDocumentoRepository;
import com.credusan.asociados.adapters.jpa.entities.TipoDocumentoEntity;
import com.credusan.asociados.domain.models.TipoDocumento;
import com.credusan.asociados.domain.ports.output.TipoDocumentoPersistence;
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
