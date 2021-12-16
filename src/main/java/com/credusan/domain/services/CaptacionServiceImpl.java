package com.credusan.domain.services;

import com.credusan.domain.models.aportes.Captacion;
import com.credusan.domain.ports.input.aportes.CaptacionService;
import com.credusan.domain.ports.output.aportes.CaptacionPersistence;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaptacionServiceImpl implements CaptacionService {

    private CaptacionPersistence repo;

    public CaptacionServiceImpl(CaptacionPersistence repo) {
        this.repo = repo;
    }

    @Override
    public List<Captacion> getAllByIdAsociado(Integer idAsociado) {
        return repo.getAllByIdAsociado(idAsociado);
    }
}
