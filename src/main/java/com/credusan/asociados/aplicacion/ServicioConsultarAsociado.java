package com.credusan.asociados.aplicacion;

import com.credusan.asociados.dominio.modelos.Asociado;
import com.credusan.asociados.dominio.puertos.PersistenciaAsociado;
import com.credusan.captaciones.dominio.puertos.PersistenciaCaptacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioConsultarAsociado {

    private final PersistenciaAsociado persistence;
    private final PersistenciaCaptacion captacionPersistence;

    public ServicioConsultarAsociado(PersistenciaAsociado persistenciaAsociado, PersistenciaCaptacion captacionPersistence) {
        this.persistence = persistenciaAsociado;
        this.captacionPersistence = captacionPersistence;
    }

    public Page<Asociado> getAll(Pageable pageable, boolean soloActivos) throws Exception {
        return persistence.getAll(pageable, soloActivos);
    }

    public Asociado getById(Integer idAsociado) throws Exception {
        Asociado asociado = persistence.getById(idAsociado);
        asociado.setActivo(captacionPersistence.getCuentaAportes(asociado.getIdAsociado()) != null);
        return asociado;
    }

    public List<Asociado> getAllByNameOrSurnames(String nombres) throws Exception {
        return persistence.getAllByNameOrSurnames(nombres);
    }


}
