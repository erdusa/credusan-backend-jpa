package com.credusan.captaciones.aplicacion;

import com.credusan.captaciones.dominio.dtos.ConsultaCaptacionExtractoDTO;
import com.credusan.captaciones.dominio.modelos.CaptacionExtracto;
import com.credusan.captaciones.dominio.puertos.PersistenciaCaptacionExtracto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class ServicioConsultarCaptacionExtracto {
    static final String DEBE_ESPECIFICAR_EL_IDENTIFICADOR_DE_LA_CAPTACION = "Debe especificar el identificador de la captación";

    private PersistenciaCaptacionExtracto repo;

    public ServicioConsultarCaptacionExtracto(PersistenciaCaptacionExtracto repo) {
        this.repo = repo;
    }

    public Page<CaptacionExtracto> getAllByIdCaptacionAndFechas(Pageable pageable, ConsultaCaptacionExtractoDTO extractoDTO) throws Exception {
        if (extractoDTO.getIdCaptacion() == null) {
            throw new Exception(DEBE_ESPECIFICAR_EL_IDENTIFICADOR_DE_LA_CAPTACION);
        }

        return repo.getAllByIdCaptacionAndFechas(pageable, extractoDTO);
    }

}
