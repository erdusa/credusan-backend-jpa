package com.credusan.asociados.infraestructura.jpa.persistencia;

import com.credusan.asociados.dominio.modelos.Asociado;
import com.credusan.asociados.dominio.puertos.PersistenciaAsociado;
import com.credusan.asociados.infraestructura.jpa.daos.RepositorioAsociado;
import com.credusan.asociados.infraestructura.jpa.entidades.EntidadAsociado;
import com.credusan.asociados.infraestructura.jpa.especificaciones.AsociadoEspec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PersistenciaAsociadoJPA implements PersistenciaAsociado {

    private RepositorioAsociado repo;

    public PersistenciaAsociadoJPA(RepositorioAsociado repo) {
        this.repo = repo;
    }

    @Override
    public Asociado save(Asociado asociado) {
        return repo.save(new EntidadAsociado(asociado)).toAsociado();
    }

    @Override
    public Asociado getById(Integer idAsociado) {
        return repo.findById(idAsociado).orElse(new EntidadAsociado()).toAsociado();
    }

    @Override
    public Page<Asociado> getAll(Pageable pageable, boolean soloActivos) {

        Specification<EntidadAsociado> espec = null;
        if (soloActivos) {
            espec = AsociadoEspec.esAsociadoActivo();
        }
        return repo.findAll(espec, pageable).map(EntidadAsociado::toAsociado);
    }

    @Override
    public List<Asociado> getAllByNameOrSurnames(String nombres) {
        return repo.findAllByNames(nombres)
                .stream()
                .map(EntidadAsociado::toAsociado)
                .collect(Collectors.toList());
    }
}
