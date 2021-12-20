package com.credusan.asociados.domain.services;

import com.credusan.asociados.domain.models.Asociado;
import com.credusan.asociados.domain.ports.output.AsociadoPersistence;
import com.credusan.captaciones.domain.ports.output.CaptacionPersistence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsociadoFinderService {

    private final AsociadoPersistence persistence;

    public AsociadoFinderService(AsociadoPersistence asociadoPersistence, CaptacionPersistence captacionPersistence) {
        this.persistence = asociadoPersistence;
    }

    public Page<Asociado> getAll(Pageable page) throws Exception {
        return persistence.getAll(page);
    }

    public Asociado getById(Integer idAsociado) throws Exception {
        return persistence.getById(idAsociado);
    }

    public List<Asociado> getAllByNames(String nombres) throws Exception {
        return persistence.getAllByNames(nombres);
    }

}
