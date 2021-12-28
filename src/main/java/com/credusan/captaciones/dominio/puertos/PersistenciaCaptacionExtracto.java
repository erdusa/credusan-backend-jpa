package com.credusan.captaciones.dominio.puertos;


import com.credusan.captaciones.dominio.dtos.ConsultaCaptacionExtractoDTO;
import com.credusan.captaciones.dominio.modelos.CaptacionExtracto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersistenciaCaptacionExtracto {

    CaptacionExtracto save(CaptacionExtracto captacionExtracto);

    Page<CaptacionExtracto> getAllByIdCaptacionAndFechas(Pageable pageable, ConsultaCaptacionExtractoDTO extractoDTO) throws Exception;
}
