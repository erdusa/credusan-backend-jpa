package com.credusan.captaciones.infraestructura.jpa.persistencia;

import com.credusan.captaciones.dominio.dtos.ConsultaCaptacionExtractoDTO;
import com.credusan.captaciones.dominio.modelos.CaptacionExtracto;
import com.credusan.captaciones.dominio.puertos.PersistenciaCaptacionExtracto;
import com.credusan.captaciones.infraestructura.jpa.daos.RepositorioCaptacionExtracto;
import com.credusan.captaciones.infraestructura.jpa.entidades.EntidadCaptacionExtracto;
import com.credusan.captaciones.infraestructura.jpa.especificaciones.CaptacionExtractoEspec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public class PersistenciaCaptacionExtractoJPA implements PersistenciaCaptacionExtracto {

    private RepositorioCaptacionExtracto repo;

    public PersistenciaCaptacionExtractoJPA(RepositorioCaptacionExtracto repo) {
        this.repo = repo;
    }

    @Override
    public CaptacionExtracto save(CaptacionExtracto captacionExtracto) {
        return repo.save(new EntidadCaptacionExtracto(captacionExtracto)).toCaptacionExtracto();
    }

    @Override
    public Page<CaptacionExtracto> getAllByIdCaptacionAndFechas(Pageable pageable, ConsultaCaptacionExtractoDTO extractoDTO) {

        Specification<EntidadCaptacionExtracto> spec = CaptacionExtractoEspec.perteneceACaptacion(extractoDTO.getIdCaptacion());
        if (extractoDTO.getFechaInicial() != null) {
            spec = spec.and(CaptacionExtractoEspec.esFechaMayorOIgualQue(extractoDTO.getFechaInicial()));
        }

        if (extractoDTO.getFechaFinal() != null) {
            spec = spec.and(CaptacionExtractoEspec.esFechaMenorOIgualQue(extractoDTO.getFechaFinal()));
        }

        return repo.findAll(spec, pageable).map(EntidadCaptacionExtracto::toCaptacionExtracto);
    }

}
