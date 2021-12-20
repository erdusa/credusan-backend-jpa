package com.credusan.captaciones.domain.services;

import com.credusan.captaciones.domain.models.Captacion;
import com.credusan.captaciones.domain.ports.output.CaptacionPersistence;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaptacionFinderService {

    private CaptacionPersistence repo;

    public CaptacionFinderService(CaptacionPersistence repo) {
        this.repo = repo;
    }

    public List<Captacion> getAllByIdAsociado(Integer idAsociado) throws Exception {
        return repo.getAllByIdAsociado(idAsociado);
    }
}
