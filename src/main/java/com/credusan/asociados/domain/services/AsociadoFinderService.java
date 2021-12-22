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
    private final CaptacionPersistence captacionPersistence;

    public AsociadoFinderService(AsociadoPersistence asociadoPersistence, CaptacionPersistence captacionPersistence) {
        this.persistence = asociadoPersistence;
        this.captacionPersistence = captacionPersistence;
    }

    public Page<Asociado> getAll(Pageable page) throws Exception {
        Page<Asociado> asociados = persistence.getAll(page);

        asociados.forEach(asociado -> asociado.setActivo(captacionPersistence.getCuentaAportes(asociado.getIdAsociado()) != null));

        return asociados;
    }

    public Asociado getById(Integer idAsociado) throws Exception {
        Asociado asociado = persistence.getById(idAsociado);
        asociado.setActivo(captacionPersistence.getCuentaAportes(asociado.getIdAsociado()) != null);
        return asociado;
    }

    public List<Asociado> getAllByNameOrSurnames(String nombres) throws Exception {
        List<Asociado> asociados = persistence.getAllByNameOrSurnames(nombres);

        asociados.forEach(asociado -> asociado.setActivo(captacionPersistence.getCuentaAportes(asociado.getIdAsociado()) != null));

        return asociados;
    }


}
