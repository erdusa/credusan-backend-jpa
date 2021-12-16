package com.credusan.adapters.jpa.aportes.persistence;

import com.credusan.adapters.jpa.aportes.daos.AsociadoRepository;
import com.credusan.adapters.jpa.aportes.entities.AsociadoEntity;
import com.credusan.domain.models.aportes.Asociado;
import com.credusan.domain.ports.output.aportes.AsociadoPersistence;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class AsociadoPersistenceJPA implements AsociadoPersistence {

    private AsociadoRepository repo;

    public AsociadoPersistenceJPA(AsociadoRepository repo) {
        this.repo = repo;
    }

    @Override
    public Asociado create(Asociado asociado) throws Exception {
        return repo.save(new AsociadoEntity(asociado)).toAsociado();
    }

    @Override
    public Asociado update(Asociado asociado) throws Exception {
        return repo.save(new AsociadoEntity(asociado)).toAsociado();
    }

    @Override
    public Asociado getById(Integer idAsociado) throws Exception {
        return repo.findById(idAsociado).orElse(new AsociadoEntity()).toAsociado();
    }

    @Override
    public List<Asociado> getAll() throws Exception {
        return repo.findAll().stream().map(AsociadoEntity::toAsociado).collect(Collectors.toList());
    }
}
