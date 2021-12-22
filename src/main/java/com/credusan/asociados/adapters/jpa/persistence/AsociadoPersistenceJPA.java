package com.credusan.asociados.adapters.jpa.persistence;

import com.credusan.asociados.adapters.jpa.daos.AsociadoRepository;
import com.credusan.asociados.adapters.jpa.entities.AsociadoEntity;
import com.credusan.asociados.domain.models.Asociado;
import com.credusan.asociados.domain.ports.output.AsociadoPersistence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class AsociadoPersistenceJPA implements AsociadoPersistence {

    private AsociadoRepository repo;

    public AsociadoPersistenceJPA(AsociadoRepository repo) {
        this.repo = repo;
    }

    @Override
    public Asociado save(Asociado asociado) throws Exception {
        return repo.save(new AsociadoEntity(asociado)).toAsociado();
    }

    @Override
    public Asociado getById(Integer idAsociado) throws Exception {
        return repo.findById(idAsociado).orElse(new AsociadoEntity()).toAsociado();
    }

    @Override
    public Page<Asociado> getAll(Pageable page) throws Exception {
        return repo.findAll(page).map(AsociadoEntity::toAsociado);
    }

    @Override
    public List<Asociado> getAllByNameOrSurnames(String nombres) throws Exception {
        return repo.findAllByNames(nombres)
                .stream()
                .map(AsociadoEntity::toAsociado)
                .collect(Collectors.toList());
    }
}
