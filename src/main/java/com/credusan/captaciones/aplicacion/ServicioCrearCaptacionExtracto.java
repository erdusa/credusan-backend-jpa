package com.credusan.captaciones.aplicacion;

import com.credusan.captaciones.dominio.modelos.CaptacionExtracto;
import com.credusan.captaciones.dominio.puertos.PersistenciaCaptacionExtracto;
import org.springframework.stereotype.Service;

@Service
public class ServicioCrearCaptacionExtracto {
    private PersistenciaCaptacionExtracto repo;

    public ServicioCrearCaptacionExtracto(PersistenciaCaptacionExtracto repo) {
        this.repo = repo;
    }

    public CaptacionExtracto create(CaptacionExtracto captacionExtracto) {
        return repo.save(captacionExtracto);
    }
}
