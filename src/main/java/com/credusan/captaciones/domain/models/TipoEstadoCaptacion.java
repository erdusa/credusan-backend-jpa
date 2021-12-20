package com.credusan.captaciones.domain.models;

import lombok.Data;

@Data
public class TipoEstadoCaptacion {
    private Integer idTipoEstadoCaptacion;
    private String nombre;

    public TipoEstadoCaptacion() {
    }

    public TipoEstadoCaptacion(Integer idTipoEstadoCaptacion) {
        this.idTipoEstadoCaptacion = idTipoEstadoCaptacion;
    }
}
